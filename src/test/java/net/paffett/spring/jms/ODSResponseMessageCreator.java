package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ODSResponseMessageCreator extends AbstractMessageCreator {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 * @param message
	 * @param header
	 */
	public ODSResponseMessageCreator(String message, JMSHeader header) {
		super(message, header);
	}

	/**
		 * 
		 */
	protected String formatMessage() {
		return (String) getMessage();
	}

	/**
		 * 
		 */
	public Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(formatMessage()));

		JMSHeader header = getHeader();

		if (header != null) {
			if (header.getMessageType() != null) {
				getTextMessage().setJMSType(header.getMessageType());
			}

			if (header.getCorrelID() != null) {
				getTextMessage().setJMSCorrelationID(header.getCorrelID());
			}

			if (header.getExpiration() > 0) {
				getTextMessage().setJMSExpiration(header.getExpiration());
			}

			if (header.getReplyTo() != null) {
				getTextMessage().setJMSReplyTo(header.getReplyTo());
			}
		}

		return getTextMessage();
	}

}
