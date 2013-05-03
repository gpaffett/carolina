package net.paffett.spring.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import org.apache.log4j.Logger;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * The TestMessageSender class uses the injected JMSTemplate to send a message
 * to a specified Queue.
 */
@Service
public class QueueSender {

	private JmsTemplate jmsTemplate;
	private Queue sendQueue;

	private static final Logger logger = Logger.getLogger(QueueSender.class);

	private static final int DEFAULT_REQUEST_RETRY_COUNT = 3;
	private static final int DEFAULT_RETRY_INTERVAL = 2000;

	private static final int DEFAULT_RESPONSE_RETRY_COUNT = 0;
	private static final int DEFUALT_FAILOVER_RETRY_INTERVAL = 10000;

	/**
	 * Sends a message using Spring's JMS Template.
	 * 
	 * @param message
	 *            the message
	 * @throws JMSException
	 *             the JMS exception
	 */
	public void sendMessage(Destination destination, GenericMessageCreator messageCreator, int retryAttempts, Boolean retry)
			throws JmsException, Exception {
		
		logger.info("About to put Message on queue. Queue[" + sendQueue.toString() + "]");

		jmsTemplate.send(destination, messageCreator);
		
		// TODO get Message ID

	}

//	public void sendMessage(String message) throws JMSException {
//		logger.debug("About to put message on queue. Queue["
//				+ sendQueue.toString() + "] Message[" + message + "]");
//		jmsTemplate.convertAndSend(sendQueue, message);
//	}

	/**
	 * Sets the JMS template.
	 * 
	 * @param template
	 *            Spring's JmsTemplate
	 */
	public void setJmsTemplate(JmsTemplate tmpl) {
		this.jmsTemplate = tmpl;
	}

	/**
	 * Sets the JMS Response Queue
	 * 
	 * @param sendQueue
	 */
	public void setSendQueue(Queue sendQueue) {
		this.sendQueue = sendQueue;
	}

}
