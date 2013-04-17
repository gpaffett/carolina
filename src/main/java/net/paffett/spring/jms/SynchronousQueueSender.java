package net.paffett.spring.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * The TestMessageSender class uses the injected JMSTemplate to send a message
 * to a specified Queue. In our case we're sending messages to 'TestQueueTwo'
 */
@Service
public class SynchronousQueueSender {
	
	private JmsTemplate jmsTemplate;
	private Queue requestQueue;
	private Queue responseQueue;
	
	private static final Logger logger = Logger
			.getLogger(SynchronousQueueSender.class);

	/**
	 * Sends and Receives a message using Spring's JMS Template.
	 * 
	 * @param message
	 *            the message
	 * @throws JMSException
	 *             the JMS exception
	 */
	public Message sendAndReceiveMessage(String message) throws JMSException {
		logger.debug("About to put message on queue. Queue["
				+ requestQueue.toString() + "] Message[" + message + "]");		
		jmsTemplate.convertAndSend(requestQueue, message);
		logger.debug("About to receive message on queue. Queue["
				+ responseQueue.toString() + "]");
		return jmsTemplate.receive(responseQueue);
	}

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
	 * Sets the JMS Request Queue
	 * 
	 * @param requestQueue
	 */
	public void setRequestQueue(Queue requestQueue) {
		this.requestQueue = requestQueue;
	}

	/**
	 * Sets the JMS Response Queue
	 * 
	 * @param responseQueue
	 */
	public void setResponseQueue(Queue responseQueue) {
		this.responseQueue = responseQueue;
	}

}
