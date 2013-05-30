package net.paffett.spring.jms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * 
 * @author gp58112
 *
 */
public abstract class XStreamMessageCreator extends AbstractMessageCreator {

	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * 
	 * @param message
	 * @param header
	 */
	public XStreamMessageCreator(Object message, JMSHeader header) {
		super(message, header);
	}
	
	/**
	 * 
	 */
	protected String formatMessage() {
		
		if (getMessage() == null) {
			throw new IllegalArgumentException("Message Element cannot be null!");
		}

		XStream xstream = new XStream();
		xstream.processAnnotations(getMessage().getClass());

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		try {
			writer.write("<?xml version=\"1.0\" ?>\n");
		} catch (IOException e) {
			log.error("Error writing XML declaration", e);
		}
		HierarchicalStreamWriter xmlWriter = new PrettyPrintWriter(writer);
		xstream.marshal(getMessage(), xmlWriter);

		String formattedMessage = new String(stream.toByteArray());

		if (log.isDebugEnabled()) {
			log.debug(formattedMessage);
		}

		return formattedMessage;
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
