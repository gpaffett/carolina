package net.paffett.spring.jms;

import javax.jms.JMSException;


/**
 * 
 * @author gp58112
 *
 */
public interface SynchronousMessageHandler {
	
	public String sendAndReceive(int retryAttempts, Boolean retry) throws JMSException, Exception;
	
	public QueueSender getQueueSender();
	
	public void setQueueSender(QueueSender queueSender);
	
//	public QueueReceiver getQueueReceiver();
	
//	public void setQueueReceiver(QueueReceiver QueueReceiver);
			
}
