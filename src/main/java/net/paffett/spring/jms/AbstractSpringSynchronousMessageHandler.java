package net.paffett.spring.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

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

	protected abstract AbstractMessageCreator createMessageCreator(String query);

	protected String sendMessage(AbstractMessageCreator messageCreator,
			Destination queue, int retryAttempts, Boolean retry)
			throws JmsException, Exception {

		String messageId = null;
		;

		try {
			log.info("Sending Message");
			jmsTemplate.send(queue, messageCreator);
		} catch (JmsException e) {
			if (retry) {
				if (retryAttempts < JMS_REQUEST_RETRY_COUNT) {
					retryAttempts = retryAttempts + 1;
					log.error("sendMessage: Caught excpetion on attempt# = "
							+ retryAttempts + ". Exception: " + e.getMessage()
							+ "\n Will Retry after " + JMS_RETRY_INTERVAL
							+ " milliseconds.");

					try {
						Thread.sleep(JMS_RETRY_INTERVAL); // sleep 2 seconds
															// before retrying.
					} catch (InterruptedException ie) {
						log.warn("sendMessage: InterruptedException during Thread.sleep call: "
								+ ie);
					}
					messageId = sendMessage(messageCreator, queue,
							retryAttempts, retry);

				} else {
					throw e; // throw exception after max retry attempts.
				}
			} else {
				log.error("MQJMSImp||sendMessage: Exception: " + e.getMessage());
				throw e;
			}

		} catch (Exception e) {
			throw e;
		}

		if (messageId == null) {
			messageId = messageCreator.getMessageId();
		}

		return messageId;

	}

	protected String receiveMessage(final String mesgId,
			final Destination receiveDestination, int retryAttempt)
			throws JMSException {
		TextMessage responseMesg = null;
		String responseMesgStr = null;
		String mesgSelector = new String("JMSCorrelationID = " + "'" + mesgId
				+ "'");
		long startTime = System.currentTimeMillis();

		try {
			responseMesg = (TextMessage) jmsTemplate.receiveSelected(
					receiveDestination, mesgSelector);

		} catch (JmsException je) {
			long waitTime = System.currentTimeMillis() - startTime;
			// Retry only during MQ FailOver / Error scenarios.
			// In other cases, timeout set at JMSTemplate will be applicable and
			// Retry should NOT be performed.
			if (waitTime < FAILOVER_RETRY_INTERVAL
					&& retryAttempt < JMS_REQUEST_RETRY_COUNT) {
				retryAttempt = retryAttempt + 1;
				log.error("MQJMSImp||receiveMessage: Caught excpetion on attempt# = "
						+ retryAttempt
						+ ". Exception: "
						+ je.getMessage()
						+ "\n Will Retry after "
						+ JMS_RETRY_INTERVAL
						+ " milliseconds.");

				try {
					Thread.sleep(JMS_RETRY_INTERVAL); // sleep 2 seconds before
														// retrying.
				} catch (InterruptedException ie) {
					log.warn("MQJMSImp||receiveMessage: InterruptedException during Thread.sleep call: "
							+ ie);
				}
				responseMesgStr = receiveMessage(mesgId, receiveDestination,
						retryAttempt); // Retry Receive operation (helps during
										// MQ fail over).
			} else {
				throw je; // throw exception after max retry attempts.
			}
		}

		if (responseMesgStr == null && responseMesg != null) {
			responseMesgStr = responseMesg.getText();
		}

		return responseMesgStr;
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