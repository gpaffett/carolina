package net.paffett.spring.jms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import net.paffett.domain.ods.reply.FDRReplyMessage;
import net.paffett.domain.ods.request.ODSSecurityElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author gp58112
 * 
 */
public abstract class ODSMessageHandler extends
		AbstractSpringSynchronousMessageHandler {

	protected Log log = LogFactory.getLog(this.getClass());

	private ODSSecurityElement odsSecurity;
	private Boolean rowDef = Boolean.TRUE;
	private Boolean columnId;
	private String query;

	public ODSMessageHandler(String query) {
		this.query = query;
	}

	public List sendAndReceive(Object[] params, int retryAttempts,
			Boolean retry) throws JMSException, Exception {

		if (odsSecurity == null) {
			throw new IllegalArgumentException(
					"ODS Security Element cannot be null!");
		}

		AbstractMessageCreator messageCreator = createMessageCreator(MessageFormat
				.format(query, params));

		String messageId = sendMessage(messageCreator, getRequestDestination(),
				retryAttempts, retry);

		log.info("Message: " + messageId + " has been Sent");

		String responseString = receiveMessage(messageId,
				getResponseDestination(), JMS_RESPONSE_RETRY_COUNT);

		if (responseString == null) { 
			log.error("JMS TimeOut occurred while receiving response.");
			throw new JMSException(
					"JMS TimeOut occurred while receiving response.");
		}
		
		List<?> list = new ArrayList();
		// TODO call mapResponse()

		return list;
	}

	private FDRReplyMessage formatResponse(String xmlString) {
		XStream xstream = new XStream();
		xstream.processAnnotations(FDRReplyMessage.class);

		return (FDRReplyMessage) xstream.fromXML(xmlString);
	}

	public ODSSecurityElement getOdsSecurity() {
		return odsSecurity;
	}

	public void setOdsSecurity(ODSSecurityElement odsSecurity) {
		this.odsSecurity = odsSecurity;
	}

	public Boolean getRowDef() {
		return rowDef;
	}

	public void setRowDef(Boolean rowDef) {
		this.rowDef = rowDef;
	}

	public Boolean getColumnId() {
		return columnId;
	}

	public void setColumnId(Boolean columnId) {
		this.columnId = columnId;
	}

}