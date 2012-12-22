/**
 * 
 */
package com.stationmillenium.coverart.web.servlets.gwtautobean.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.SongItemRepository;
import com.stationmillenium.coverart.web.gwt.admin.client.server.autobean.AdminAutobeanFactory;
import com.stationmillenium.coverart.web.gwt.admin.shared.autobean.PlaylistExtract;

/**
 * Servlet for playlist extract
 * @author vincent
 *
 */
@SuppressWarnings("serial")
@WebServlet("/admin/playlistExtract")
@Configurable
public class PlaylistExtractServlet extends HttpServlet {

	//logger
	private static Logger LOG = LoggerFactory.getLogger(PlaylistExtractServlet.class);
	
	//values for file generation
	@Value("${playlistExtract.fileNamePrefix}") private String fileNamePrefix;
	@Value("${playlistExtract.fileTimeFormat}") private String fileTimeFormat;
	@Value("${playlistExtract.dateTimeFormat}") private String dateTimeFormat;
	@Value("${playlistExtract.fromDateText}") private String fromDateText;
	@Value("${playlistExtract.toDateText}") private String toDateText;
	@Value("${playlistExtract.itemCount}") private String itemCount;
	@Value("${playlistExtract.playedTimeColumn}") private String playedTimeColumn;
	@Value("${playlistExtract.artistColumn}") private String artistColumn;
	@Value("${playlistExtract.titleColumn}") private String titleColumn;
	
	//songs repository
	@Autowired
	private SongItemRepository repository;
	
	//local instances
	private SimpleDateFormat sdfDateTime;
	private SimpleDateFormat sdfFileFormat;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PlaylistExtract playlistExtract = deserialize(req);		
		List<SongHistoryItemDTO> songList = getDataFormDB(playlistExtract);		
		Workbook workbook = generateXLSXSheet(playlistExtract, songList);
		
		//set headers
		
		//push file on response output stream
		String fileDate = sdfFileFormat.format(Calendar.getInstance().getTime());
		resp.setContentType(" application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.addHeader("Content-disposition", "attachment; filename=\"" + fileNamePrefix + fileDate + ".xlsx\"");
		workbook.write(resp.getOutputStream());
	}

	/**
	 * Generate the XLSX sheet
	 * @param playlistExtract the playlist extract data
	 * @param songList the gathered song
	 * @return the {@link Workbook} for the sheet
	 */
	private Workbook generateXLSXSheet(PlaylistExtract playlistExtract, List<SongHistoryItemDTO> songList) {
		if (sdfDateTime == null)
			sdfDateTime = new SimpleDateFormat(dateTimeFormat); //date formater
		if (sdfFileFormat == null)
			sdfFileFormat = new SimpleDateFormat(fileTimeFormat); //date formater
		
		//generate xlsx sheet 
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		
		//init first row
		Row topRow = sheet.createRow(0);
		topRow.createCell(0).setCellValue(fromDateText);
		topRow.createCell(1).setCellValue(sdfDateTime.format(playlistExtract.getStartExtractDate()));
		topRow.createCell(2).setCellValue(toDateText);
		topRow.createCell(3).setCellValue(sdfDateTime.format(playlistExtract.getEndExtractDate()));
		sheet.createRow(1); //new blank line
		
		//item count row
		Row itemCountRow = sheet.createRow(2);
		itemCountRow.createCell(0).setCellValue(itemCount);
		itemCountRow.createCell(1).setCellValue(songList.size());
		sheet.createRow(3); //new blank line
		
		//table titles row
		Row tableTitlesRow = sheet.createRow(4);
		tableTitlesRow.createCell(0).setCellValue(playedTimeColumn);
		tableTitlesRow.createCell(1).setCellValue(artistColumn);
		tableTitlesRow.createCell(2).setCellValue(titleColumn);
		
		//add playlist extract line		
		int index = 5;		
		for (SongHistoryItemDTO song : songList) {
			Row tableRow = sheet.createRow(index);
			tableRow.createCell(0).setCellValue(sdfDateTime.format(song.getPlayedDate().getTime()));
			tableRow.createCell(1).setCellValue(song.getArtist());
			tableRow.createCell(2).setCellValue(song.getTitle());
			index++;
		}
		return workbook;
	}

	/**
	 * Query data from database
	 * @param playlistExtract the bean to get param
	 * @return the list of {@link SongHistoryItemDTO}
	 */
	private List<SongHistoryItemDTO>  getDataFormDB(PlaylistExtract playlistExtract) {
		//get data
		Calendar minDate = Calendar.getInstance();
		Calendar maxDate = Calendar.getInstance();
		minDate.setTime(playlistExtract.getStartExtractDate());
		maxDate.setTime(playlistExtract.getEndExtractDate());
		List<SongHistoryItemDTO> songList = repository.getSongsPlayedBetween2Dates(minDate, maxDate);
		LOG.debug("Gathered song list : " + songList);
		return songList;
	}

	/**
	 * Deserialize data 
	 * @param req the request to get data
	 * @return the {@link PlaylistExtract} bean
	 */
	private PlaylistExtract deserialize(HttpServletRequest req) {
		//get json
		String json = req.getParameter("jsonContent");
		LOG.debug("Gathered JSON : " + json);
		
		//deserialize
		AutoBeanFactory factory = AutoBeanFactorySource.create(AdminAutobeanFactory.class);
		AutoBean<PlaylistExtract> autobean = AutoBeanCodex.decode(factory, PlaylistExtract.class, json);
		PlaylistExtract playlistExtract = autobean.as();
		return playlistExtract;
	}
}
