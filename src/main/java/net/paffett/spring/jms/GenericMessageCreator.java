package net.paffett.spring.jms;

import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public abstract class GenericMessageCreator implements MessageCreator {

	protected String payload;
	protected TextMessage textMessage;
	protected JMSHeader header = null;
	
	protected abstract String formatPayload(Class<?> clazz);
	
	public GenericMessageCreator() {
		super();
	}

	public String getMessageId() throws Exception {
		return textMessage.getJMSMessageID();
	}

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public JMSHeader getHeader() {
		return header;
	}

	public void setHeader(JMSHeader header) {
		this.header = header;
	}
		
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
}