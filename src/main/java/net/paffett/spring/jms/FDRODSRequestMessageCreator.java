package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class FDRODSRequestMessageCreator extends GenericMessageCreator {
	

	public Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(message));

		if (null != jmsHeader) {
			getTextMessage().setJMSType(jmsHeader.getMessageType());
			getTextMessage().setJMSCorrelationID(jmsHeader.getCorrelID());
			getTextMessage().setJMSExpiration(jmsHeader.getExpiration());
			getTextMessage().setJMSReplyTo(jmsHeader.getReplyTo());
		}

		return getTextMessage();
	}

}
