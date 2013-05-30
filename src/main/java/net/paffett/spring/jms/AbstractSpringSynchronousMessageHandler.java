package net.paffett.spring.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * 
 * @author gp58112
 * 
 */
public abstract class AbstractSpringSynchronousMessageHandler implements
		SynchronousMessageHandler {

	protected JmsTemplate jmsTemplate;

	protected Log log = LogFactory.getLog(this.getClass());

	protected static final int JMS_REQUEST_RETRY_COUNT = 3;
	protected static final int JMS_RESPONSE_RETRY_COUNT = 0;
	protected static final int JMS_RETRY_INTERVAL = 2000;
	protected static final int FAILOVER_RETRY_INTERVAL = 10000;

	private Destination requestDestination;
	private Destination responseDestination;

	protected String sendMessage(Object message, Destination queue)
			throws JmsException, JMSException {

		ReferenceHolderMessagePostProcessor messagePostProcessor = new ReferenceHolderMessagePostProcessor();

		try {
			log.info("Sending Message");
			jmsTemplate.convertAndSend(queue, message, messagePostProcessor);
		} catch (JmsException e) {
			// TODO Handle JMSException
			log.error("Failure in message Convert and Send", e);
		}

		Message sentMessage = messagePostProcessor.getSentMessage();

		return sentMessage.getJMSMessageID();

	}

	protected Object receiveMessage(final String messageId,
			final Destination receiveDestination) throws JMSException {
		Object responseMessage = null;

		String messageSelector = new String("JMSCorrelationID = " + "'"
				+ messageId + "'");
		try {
			responseMessage = jmsTemplate.receiveSelectedAndConvert(
					receiveDestination, messageSelector);
		} catch (JmsException je) {
			// TODO Handle JMSException
		}
		
		return responseMessage;
	}

	public Destination getResponseDestination() {
		return responseDestination;
	}

	/**
	 * @param receiveDestination
	 *            the receiveDestination to set
	 */
	public void setResponseDestination(Destination responseDestination) {
		this.responseDestination = responseDestination;
	}

	/**
	 * @return the sendDestination
	 */
	public Destination getRequestDestination() {
		return requestDestination;
	}

	/**
	 * @param responseDestination
	 *            the receiveDestination to set
	 */
	public void setRequestDestination(Destination requestDestination) {
		this.requestDestination = requestDestination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}