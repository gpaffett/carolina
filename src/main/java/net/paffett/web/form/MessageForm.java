package net.paffett.web.form;

import java.util.Date;

public class MessageForm {
	
	private String messageText;
		
	public MessageForm() {}

	public MessageForm(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
}