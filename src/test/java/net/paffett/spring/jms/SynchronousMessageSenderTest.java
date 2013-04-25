package net.paffett.spring.jms;

import java.util.ArrayList;
import java.util.List;

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
			List<String> params = new ArrayList<String>();
			params.add("George");
			
			aci.query(params);
		} catch (Exception e) {
			log.error("Oh Crap", e);
		}

	}

}
