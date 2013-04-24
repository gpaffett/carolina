package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public abstract class FDRRequestMessageCreator implements MessageCreator {

	protected String message;
	protected TextMessage textMessage;
	protected JMSHeaderVO jmsHeader = null;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() throws Exception {
		return textMessage.getJMSMessageID();
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public JMSHeaderVO getJmsHeader() {
		return jmsHeader;
	}

	public void setJmsHeaderVO(JMSHeaderVO jmsHeaderVO) {
		this.jmsHeader = jmsHeaderVO;
	}

	public abstract Message createMessage(Session session) throws JMSException;
}
