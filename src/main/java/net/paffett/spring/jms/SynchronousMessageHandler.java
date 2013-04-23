package net.paffett.spring.jms;

import java.util.Map;

import javax.jms.JMSException;

import com.ecount.core.exceptions.ECSJMSException;



/**
 * 
 * @author gp58112
 *
 */
public interface SynchronousMessageHandler {

	public String sendAndReceive(int retryAttempts, Boolean retry) throws JMSException, Exception ;
		
}
