package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.core.MessagePostProcessor;

public class ReferenceHolderMessagePostProcessor implements
		MessagePostProcessor {

	protected Message sentMessage;

	/**
	 * Return the sent message.
	 */
	public Message getSentMessage() {
		return sentMessage;
	}

	@Override
	public Message postProcessMessage(Message message) throws JMSException {
		// keep a reference on the messge
		this.sentMessage = message;
		return message;
	}

}
