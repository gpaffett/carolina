package net.paffett.spring.jms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.MessageCreator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class FDRODSRequestMessageCreator implements MessageCreator {

	protected Log log = LogFactory.getLog(this.getClass());
	
	ODSSecurityElement security = new ODSSecurityElement();
	ODSRequestElement request = new ODSRequestElement();
	FDRRequestMessage fdrRequest = new FDRRequestMessage();

	private String message;
	private TextMessage textMessage;
	private JMSHeader jmsHeader = null;

	public void setQuery(String query, Object[] parameters) {
		
		String  queryText = MessageFormat.format(query, parameters);		
		this.message = formatMessageText(queryText);
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

	public JMSHeader getJmsHeaderVO() {
		return jmsHeader;
	}

	public void setJmsHeaderVO(JMSHeader jmsHeaderVO) {
		this.jmsHeader = jmsHeaderVO;
	}

	public Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(message));

		if (null != jmsHeader && jmsHeader.getMessageType().length() > 0) {
			getTextMessage().setJMSType(jmsHeader.getMessageType());
		}

		if (null != jmsHeader && jmsHeader.getCorrelID().length() > 0) {
			getTextMessage().setJMSCorrelationID(jmsHeader.getCorrelID());
		}

		if (null != jmsHeader && jmsHeader.getExpiration() > 0) {
			getTextMessage().setJMSExpiration(jmsHeader.getExpiration());
		}

		return getTextMessage();
	}

	private String formatMessageText(String query) {
		
		request.setSelect(query);
		fdrRequest.setSecurityElement(security);
		fdrRequest.addRequestElement(request);
		
		XStream xstream = new XStream();
		xstream.processAnnotations(FDRRequestMessage.class);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		try {
			writer.write("<?xml version=\"1.0\" ?>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Oh Crap", e);
		}
		HierarchicalStreamWriter xmlWriter = new PrettyPrintWriter(writer);
		xstream.marshal(fdrRequest, xmlWriter);
		return new String(stream.toByteArray());
	}

	public void setUserId(String odsUserId) {
		security.setUserId(odsUserId);
	}

	public void setPassword(String password) {
		security.setPasswd(password);
	}

	public void setRowDef(Boolean rowDef) {
		request.setRowDef(rowDef);
	}

	public void setUserData(String userData) {
		request.setUserData(userData);
	}

}
