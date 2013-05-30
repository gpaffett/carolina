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
				 
				JMSHeader header = new JMSHeader();
				header.setCorrelID(message.getJMSMessageID());
				String responseMessage = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"0\"><ROWSET ROWS=\"2\">" +
	                    "<ROWDEF><COLUMN ID=\"ATV_RIS_CTL_FLG\" LEN=\"1\" NULL=\"N\"/></ROWDEF>" +
						"<ROW><C>1</C></ROW></ROWSET></ODSREPLY></FDR>";
				
				ODSResponseMessageCreator mc = new ODSResponseMessageCreator(responseMessage, header);
					
				logger.debug("About to process message: ");

				/* call message sender to put message onto second queue */
				queueSender.sendMessage(message.getJMSReplyTo(), mc, 1, true);

			} catch (JMSException jmsEx) {
				String errMsg = "An error occurred extracting message";
				logger.error(errMsg, jmsEx);
			} catch (Exception e ) {
				String errMsg = "An error occurred extracting message";
				logger.error(errMsg, e);
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
