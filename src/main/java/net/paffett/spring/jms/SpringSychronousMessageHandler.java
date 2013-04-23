package net.paffett.spring.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 
 * @author gp58112
 *
 */
public abstract class SpringSychronousMessageHandler implements
		SynchronousMessageHandler {

	private JmsTemplate jmsTemplate;
	private static final Log log = LogFactory
			.getLog(SpringSychronousMessageHandler.class);
	private static final int JMS_REQUEST_RETRY_COUNT = 3;
	private static final int JMS_RESPONSE_RETRY_COUNT = 0;
	private static final int JMS_RETRY_INTERVAL = 2000;
	private static final int FAILOVER_RETRY_INTERVAL = 10000;

	private ConnectionFactory connectionFactory;
	private Destination requestDestination;
	private Destination responseDestination;	

	protected abstract MessageCreator getMessageCreator();
	
	public String sendAndReceive(int retryAttempts, Boolean retry) throws JMSException, Exception {
					
		String messageId  = sendMessage(getSendDestination(), retryAttempts, retry);

		String responseMessage = receiveMessage(messageId, getReceiveDestination(), JMS_RESPONSE_RETRY_COUNT );

		if (responseMessage == null) {
			//TODO Enhance Error Message
			log.error("");
			throw new JMSException(
					"JMS TimeOut occurred while receiving response.");
		}

		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getSimpleName() + "Respionce received: ["
					+  responseMessage + "]");
		}
		
		return responseMessage;
	}

	private String sendMessage(final Destination destination, int retryAttempts, Boolean retry)
			throws JmsException, Exception {
		
		String messageId = null;
		
		MessageCreator messageCreator = getMessageCreator();

		try {
			log.info("Sending Message");
			jmsTemplate.convertAndSend(destination, messageCreator.createMessage(null));
		} catch (JmsException e) {
			if (retry) {
				if (retryAttempts < JMS_REQUEST_RETRY_COUNT) {
					retryAttempts = retryAttempts + 1;
					log.error("sendMessage: Caught excpetion on attempt# = "
							+ retryAttempts
							+ ". Exception: "
							+ e.getMessage()
							+ "\n Will Retry after "
							+ JMS_RETRY_INTERVAL
							+ " milliseconds.");

					try {
						Thread.sleep(JMS_RETRY_INTERVAL); // sleep 2 seconds
															// before retrying.
					} catch (InterruptedException ie) {
						log.warn("sendMessage: InterruptedException during Thread.sleep call: "
								+ ie);
					}
					messageId = sendMessage(destination, retryAttempts, retry); 
					
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

		//TODO get Message ID
		
		return messageId;

	}

	private String receiveMessage(final String mesgId,
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


	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;

	}

	public Destination getReceiveDestination() throws JMSException {
		return responseDestination;
	}

	/**
	 * @param receiveDestination
	 *            the receiveDestination to set
	 */
	public void setReceiveDestination(Destination receiveDestination) {
		this.responseDestination = receiveDestination;
	}

	/**
	 * @return the sendDestination
	 */
	public Destination getSendDestination() throws JMSException {
		return requestDestination;
	}

	/**
	 * @param responseDestination
	 *            the receiveDestination to set
	 */
	public void setSendDestination(Destination sendDestination) {
		this.requestDestination = sendDestination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

}
