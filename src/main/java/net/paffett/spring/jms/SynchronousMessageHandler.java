package net.paffett.spring.jms;

import java.util.List;

import javax.jms.JMSException;

/**
 * 
 * @author gp58112
 * 
 */
public interface SynchronousMessageHandler {

	public List sendAndReceive(Object[] params, int retryAttempts,
			Boolean retry) throws JMSException, Exception;

}
