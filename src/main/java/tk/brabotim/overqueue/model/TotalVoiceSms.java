package tk.brabotim.overqueue.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import br.com.totalvoice.TotalVoiceClient;
import br.com.totalvoice.api.Sms;
import tk.brabotim.overqueue.controllers.ClientController;

public class TotalVoiceSms {
	
	static final Logger logger = LogManager.getLogger(ClientController.class);
	
	private String phoneNumber;
	private String messageContent;
	private String token = "ac292cc192447e1294cb4ce6d74bf2a4";
	
	public void sendSms(String phoneNumber, String messageContent) throws Exception {
		
		logger.info("Sending SMS to number {}", phoneNumber);
		logger.info("SMS text content {}", messageContent);
		try {
			TotalVoiceClient client = new TotalVoiceClient(token);
			Sms sms = new Sms(client);
			
			JSONObject result = sms.enviar(phoneNumber, messageContent);
			logger.info("SMS JSON result: {}", result);
		} catch (Exception ex) {
			logger.info("Error sending SMS: {}", ex.getMessage());
			throw new Exception();
		}
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
}
