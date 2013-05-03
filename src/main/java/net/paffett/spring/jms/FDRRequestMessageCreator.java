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

import org.springframework.jms.core.MessageCreator;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public abstract class FDRRequestMessageCreator extends GenericMessageCreator {

	protected String message;
	protected TextMessage textMessage;
	protected JMSHeader header;
		
	protected FDRRequestMessage fdrRequest = new FDRRequestMessage();

	public abstract Message createMessage(Session session) throws JMSException;

	public abstract void addODSRequest(Boolean rowDef, Boolean columnDef, String odsRequest );
	
	protected String formatPayload(Class<?> clazz) {		
		
		XStream xstream = new XStream();
		xstream.processAnnotations(clazz);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		try {
			writer.write("<?xml version=\"1.0\" ?>\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Oh Crap", e);
		}
		HierarchicalStreamWriter xmlWriter = new PrettyPrintWriter(writer);
		xstream.marshal(payload, xmlWriter);

		return new String(stream.toByteArray());
	}

	public void setUserId(String odsUserId) {
		fdrRequest.setSecurityUserId(odsUserId);
	}

	public void setPassword(String password) {
		fdrRequest.setSecurityPassword(password);
	}

	public void setRowDef(Boolean rowDef) {
		request.setRowDef(rowDef);
	}

	public void setUserData(String userData) {
		request.setUserData(userData);
	}

}
