package com.stationmillenium.coverart.services.alerts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.stationmillenium.coverart.repositories.AlertRepository;
import com.stationmillenium.coverart.web.gwt.admin.shared.requestfactory.alerts.AlertType;

/**
 * Service to emit alerts
 * @author vincent
 *
 */
@Service
public class AlertService {

	//logger
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertService.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	//alert repository
	@Autowired
	private AlertRepository alertRepository;
	
	//mail sender
	@Autowired
	private MailSender mailSender;
	
	//mail templates
	@Autowired @Qualifier("fmActiveAlertTemplate") private SimpleMailMessage fmActiveAlertTemplate;
	@Autowired @Qualifier("fmEndedAlertTemplate") private SimpleMailMessage fmEndedAlertTemplate;
	@Autowired @Qualifier("shoutcastActiveAlertTemplate") private SimpleMailMessage shoutcastActiveAlertTemplate;
	@Autowired @Qualifier("shoutcastEndedAlertTemplate") private SimpleMailMessage shoutcastEndedAlertTemplate;
	@Autowired @Qualifier("playlistActiveAlertTemplate") private SimpleMailMessage playlistActiveAlertTemplate;
	@Autowired @Qualifier("playlistEndedAlertTemplate") private SimpleMailMessage playlistEndedAlertTemplate;
	
	/**
	 * Send an alert email
	 * @param type the alert type
	 * @param activeAlert if the alert is active or not
	 */
	private void sendAlert(AlertType type, boolean activeAlert) {
		if (alertRepository.getAlertEnabledForType(type)) { //if alert enabled
			List<String> emailList = alertRepository.getEmailFromAlertType(type); //load email adress
			if (emailList.size() > 0) { //if some adresses found
				switch (type) {				
				case FM:				
					if (activeAlert) {
						LOGGER.debug("Send active alert mail for FM");					
						sendMail(fmActiveAlertTemplate, emailList);
					} else {
						LOGGER.debug("Send ended alert mail for FM");					
						sendMail(fmEndedAlertTemplate, emailList);
					}
					break;
					
				case PLAYLIST:
					if (activeAlert) {
						LOGGER.debug("Send active alert mail for playlist");					
						sendMail(playlistActiveAlertTemplate, emailList);
					} else {
						LOGGER.debug("Send ended alert mail for playlist");					
						sendMail(playlistEndedAlertTemplate, emailList);
					}
					break;
				
				case SHOUTCAST:
					if (activeAlert) {
						LOGGER.debug("Send active alert mail for shoutcast");					
						sendMail(shoutcastActiveAlertTemplate, emailList);
					} else {
						LOGGER.debug("Send ended alert mail for shoutcast");					
						sendMail(shoutcastEndedAlertTemplate, emailList);
					}
					break;
				}
			} else
				LOGGER.debug("Alert disabled : " + type);
		} else
			LOGGER.debug("No mail to send");
	}
	
	/**
	 * Add date to mail
	 * @param originalMessage the original message
	 * @return the original message with date added
	 */
	private String computeMailMessage(String originalMessage) {
		//format date
		Calendar calendar = Calendar.getInstance();
		String dateText = sdf.format(calendar.getTime());		
		return originalMessage + dateText; //add date to text
	}
	
	/**
	 * Send alert mail
	 * @param messageTemplate the message template
	 * @param emailAdressList the email adress list
	 */
	private void sendMail(SimpleMailMessage messageTemplate, List<String> emailAdressList) {
		SimpleMailMessage message = new SimpleMailMessage(messageTemplate); //copy template message
		message.setTo(emailAdressList.toArray(new String[0])); //set recipients
		String messageText = computeMailMessage(message.getText()); //message text
		message.setText(messageText);
		
		try {
			mailSender.send(message); //send message
			LOGGER.info("Sent alert mail : " + message);
		} catch (MailException e) {
			LOGGER.warn("Error while send alert mail", e);
		}		
	}
	
	/**
	 * Send an active alert
	 * @param type the type of the alert
	 */
	public void sendActiveAlert(AlertType type) {
		LOGGER.warn("Send active alert : " + type);
		sendAlert(type, true);
	}
	
	/**
	 * Send an ended alert
	 * @param type the type of the alert
	 */
	public void sendEndedAlert(AlertType type) {
		LOGGER.info("Send ended alert : " + type);
		sendAlert(type, false);
	}
	
}
