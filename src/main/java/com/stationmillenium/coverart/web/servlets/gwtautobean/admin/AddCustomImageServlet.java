/**
 * 
 */
package com.stationmillenium.coverart.web.servlets.gwtautobean.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.stationmillenium.coverart.beans.utils.GeneralPropertiesBean;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.dto.services.images.SongImageDTO;
import com.stationmillenium.coverart.exceptions.CustomImageException;
import com.stationmillenium.coverart.services.covergraber.CustomImageService;
import com.stationmillenium.coverart.web.gwt.admin.client.server.requestfactory.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageParam;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.autobean.AddCustomImageReport;

/**
 * Servlet for adding custom image to song
 * @author vincent
 *
 */
@SuppressWarnings("serial")
@WebServlet("/admin/addCustomImage")
@Configurable
public class AddCustomImageServlet extends HttpServlet {

	//logger
	private static Logger LOGGER = LoggerFactory.getLogger(AddCustomImageServlet.class);
	
	//service
	//custom image service
	@Autowired
	private CustomImageService customImageService;
	
	//config
	@Autowired
	private GeneralPropertiesBean config;
		
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SongImageDTO songImageReturnedDTO = null;
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(req); //check if mutlipart request
		if (isMultipart) {
			try {
				//init var
				FileItem file = null;
				SongHistoryItemDTO songItem = null;
				
				//init files retriever
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(req);
				LOGGER.debug("List of gathered files : " + items);
				
				//process file list
				for (FileItem fileItem : items) {
					if (!fileItem.isFormField()) {
						LOGGER.debug("File field : " + fileItem);
						file = fileItem;
						LOGGER.debug("Written image : " + songImageReturnedDTO);
						
					} else {
						LOGGER.debug("Regular form field : " + fileItem.getFieldName());
						if (fileItem.getFieldName().equals("json")) {
							//see : http://stackoverflow.com/questions/5021295/servlet-file-upload-filename-encoding
							String paramJSON = Streams.asString(fileItem.getInputStream(), "UTF-8");
							LOGGER.debug("Gathered JSON data : "  + paramJSON);
							songItem = extractParamFromJSON(paramJSON);
						}
					}
				}
				
				//process data
				songImageReturnedDTO = customImageService.addCustomImageToDirectory(file.getInputStream());
				customImageService.linkCustomImageToFile(songItem, songImageReturnedDTO);
				
			} catch (CustomImageException | FileUploadException e) {
				LOGGER.warn("Error during import of custom image", e);
			}
			
		} else
			LOGGER.warn("Not a multipart request - abording");
		
		//set headers
		resp.setContentType("application/json");
		
		//push json on response output stream
		String json = serializeJSON(songImageReturnedDTO);
		resp.getWriter().append(json);
		resp.getWriter().flush();
	}

	/**
	 * Extract data from JSON
	 * @param json the JSON
	 * @return the params as {@link SongHistoryItemDTO}
	 */
	private SongHistoryItemDTO extractParamFromJSON(String json) {
		//extract data
		AutoBeanFactory factory = AutoBeanFactorySource.create(AdminAutobeanFactory.class);
		AutoBean<AddCustomImageParam> autobean = AutoBeanCodex.decode(factory, AddCustomImageParam.class, json); 
		AddCustomImageParam params = autobean.as();
		
		//process data
		SongHistoryItemDTO item = new SongHistoryItemDTO();
		item.setArtist(params.getArtist());
		item.setTitle(params.getTitle());
		LOGGER.debug("Parameters from JSON : " + item);
		return item;
	}

	/**
	 * Get the returned JSON data 
	 * @param songImage the {@link SongImageDTO} as input data
	 * @return the JSON as {@link String}
	 */
	private String serializeJSON(SongImageDTO songImage) {
		//init autobean
		AutoBeanFactory factory = AutoBeanFactorySource.create(AdminAutobeanFactory.class);
		AutoBean<AddCustomImageReport> autobean = factory.create(AddCustomImageReport.class);
		AddCustomImageReport addCustomImageReport = autobean.as();
		
		//fill in autobean
		if (songImage != null) {
			addCustomImageReport.setAddOK(true);
			addCustomImageReport.setFileName(config.getCoverImagesPath() + "/" + songImage.getFileName());
			addCustomImageReport.setHeight(songImage.getHeight());
			addCustomImageReport.setWidth(songImage.getWidth());
		} else
			addCustomImageReport.setAddOK(false);
		
		//serialize to JSON
		String json = AutoBeanCodex.encode(autobean).getPayload();
		LOGGER.debug("Return JSON : " + json);
		
		return json;
	}
	
}
