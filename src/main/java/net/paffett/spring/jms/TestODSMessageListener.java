package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

/**
 * Class handles incoming messages
 * 
 * @see PointOfIssueMessageEvent
 */
public class TestODSMessageListener implements MessageListener {

	private QueueSender queueSender;
	private static final Logger logger = Logger
			.getLogger(TestODSMessageListener.class);

	/**
	 * Method implements JMS onMessage and acts as the entry point for messages
	 * consumed by Springs DefaultMessageListenerContainer. When
	 * DefaultMessageListenerContainer picks a message from the queue it invokes
	 * this method with the message payload.
	 */
	public void onMessage(Message message) {
		logger.debug("Received message from queue [" + message + "]");

		/* The message must be of type TextMessage */
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				logger.debug("About to process message: " + msgText);

				/* call message sender to put message onto second queue */
				queueSender.sendMessage(msgText);

			} catch (JMSException jmsEx) {
				String errMsg = "An error occurred extracting message";
				logger.error(errMsg, jmsEx);
			}
		} else {
			String errMsg = "Message is not of expected type TextMessage";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}

	/**
	 * Sets the message sender.
	 * 
	 * @param queueSender
	 *            the new message sender
	 */
	public void setQueueSender(QueueSender queueSender) {
		this.queueSender = queueSender;
	}
}
