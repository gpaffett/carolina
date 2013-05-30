package net.paffett.spring.jms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.paffett.domain.ods.reply.FDRReplyMessage;
import net.paffett.domain.ods.request.FDRRequestMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class XStreamFDRMessageConverter implements MessageConverter {

	XStream xstream = new XStream();
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public Message toMessage(Object object, Session session)
			throws JMSException, MessageConversionException {
	
		xstream.processAnnotations(object.getClass());

		if ( log.isDebugEnabled() ) {
			log.debug("Object to be converter to Message: " + object);
		}
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		
		try {
			writer.write("<?xml version=\"1.0\" ?>\n");
		} catch (IOException e) {
			//TODO Handle IOException of writing XML declaration 
		}
		HierarchicalStreamWriter xmlWriter = new PrettyPrintWriter(writer);
		xstream.marshal(object, xmlWriter);

		String formattedMessage = new String(stream.toByteArray());
		
		if ( log.isDebugEnabled() ) {
			log.debug("Message Text: " + formattedMessage);
		}
			
		TextMessage message = session.createTextMessage(formattedMessage);

		message.setJMSReplyTo(((FDRRequestMessage)object).getReplyTo());
		return message;		
	}

	@Override
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		
		xstream.processAnnotations(FDRReplyMessage.class);
		String xmlResponse =  ((TextMessage)message).getText();

		Object reply = xstream.fromXML(xmlResponse);
		
		if ( log.isDebugEnabled() ) {
			log.debug("Object converted from Message: " + reply);
		}
		
		return reply;
	}

	
}
