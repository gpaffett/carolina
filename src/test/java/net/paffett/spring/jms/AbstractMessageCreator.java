package net.paffett.spring.jms;

import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public abstract class AbstractMessageCreator implements MessageCreator {

	private Object message;
	private TextMessage textMessage;
	private JMSHeader header = null;

	protected abstract String formatMessage();

	public AbstractMessageCreator(Object message, JMSHeader header) {
		this.message = message;
		this.header = header;
	}

	public String getMessageId() throws Exception {
		return textMessage.getJMSMessageID();
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	protected void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	protected Object getMessage() {
		return message;
	}

	protected void setMessage(Object message) {
		this.message = message;
	}

	public JMSHeader getHeader() {
		return header;
	}
	
	public void setHeader(JMSHeader header) {
		this.header = header;
	}

}