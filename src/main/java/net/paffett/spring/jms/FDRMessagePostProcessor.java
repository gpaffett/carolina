package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessagePostProcessor;

public abstract class FDRMessagePostProcessor implements MessagePostProcessor {

	public Message postProcessMessage(Message message) throws JMSException {
		message.setIntProperty("AccountID", 1234);
		message.setJMSCorrelationID("123-00001");
		return message;
	}

	public abstract Message createMessage(Session session) throws JMSException;
}
