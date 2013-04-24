package net.paffett.spring.jms;

import javax.jms.JMSException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SynchronousMessageSenderTest extends TestCase {

	protected Log log = LogFactory.getLog(this.getClass());

	private ActivationCodeInquiry aci;

	protected void setUp() throws Exception {
		super.setUp();
			
		ApplicationContext context =
			    new ClassPathXmlApplicationContext(new String[] {"spring-config.xml"} );
		
		aci = (ActivationCodeInquiry) context.getBean("activationCodeInquiry");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testActivationInquiry() {

		try {
			aci.sendAndReceive(1, Boolean.FALSE);
		} catch (JMSException e) {
			log.error("Oh Crap", e);
		} catch (Exception e) {
			log.error("Oh Crap", e);
		}

	}

}
