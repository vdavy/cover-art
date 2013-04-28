package com.stationmillenium.coverart.services.alerts;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.dto.services.history.SongHistoryItemDTO;
import com.stationmillenium.coverart.repositories.AlertRepository;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Custom image alert service to send alert when custom images are missing
 * @author vincent
 *
 */
@Service
public class CustomImageMissingAlertService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomImageMissingAlertService.class);
	
	//alert repository
	@Autowired
	private AlertRepository alertRepository;
	
	//mail sender
	@Autowired
	private MailSender mailSender;
	
	//mail templates
	@Autowired @Qualifier("missingCustomImagesAlertTemplate") 
	private SimpleMailMessage missingCustomImagesAlertTemplate;
	
	/**
	 * Send an alert email
	 * @param type the alert type
	 * @param activeAlert if the alert is active or not
	 */
	public void sendAlert(List<SongHistoryItemDTO> missingCustomImagesSongList) {
		if (alertRepository.getAlertEnabledForType(AlertType.CUSTOM_IMAGE)) { //if alert enabled
			List<String> emailList = alertRepository.getEmailFromAlertType(AlertType.CUSTOM_IMAGE); //load email adress
			if (emailList.size() > 0) { //if some adresses found
				
				LOGGER.debug("Send missing custom images alert mail");
				computeMailMessage(missingCustomImagesSongList); //message text
				sendMail(missingCustomImagesAlertTemplate, emailList);				
			} else
				LOGGER.debug("Missing custom images alert disabled");
		} else
			LOGGER.debug("No mail to send");
	}
	
	/**
	 * Set the song list into the message text
	 * @param missingCustomImagesSongList the song list
	 */
	private void computeMailMessage(List<SongHistoryItemDTO> missingCustomImagesSongList) {
		StringBuilder sb = new StringBuilder(missingCustomImagesAlertTemplate.getText());
		sb.append("\n");
		
		//process list of songs
		for (SongHistoryItemDTO song : missingCustomImagesSongList) {
			sb.append(song.getArtist());
			sb.append(" - ");
			sb.append(song.getTitle());
			sb.append("\n");
		}		
		
		//set finalized messagee text
		missingCustomImagesAlertTemplate.setText(sb.toString());
	}
	
	/**
	 * Send alert mail
	 * @param messageTemplate the message template
	 * @param emailAdressList the email adress list
	 */
	private void sendMail(SimpleMailMessage messageTemplate, List<String> emailAdressList) {
		SimpleMailMessage message = new SimpleMailMessage(messageTemplate); //copy template message
		message.setTo(emailAdressList.toArray(new String[0])); //set recipients
		
		try {
			mailSender.send(message); //send message
			LOGGER.info("Sent alert mail : " + message);
		} catch (MailException e) {
			LOGGER.warn("Error while send alert mail", e);
		}		
	}
	
}
