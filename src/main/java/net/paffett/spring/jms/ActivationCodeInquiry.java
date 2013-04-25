package net.paffett.spring.jms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class ActivationCodeInquiry extends SpringSychronousMessageHandler {

	private String query = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = \"{0}\"";
	
	public List<?> query(List<?> params) {
		List<String> list = new ArrayList<String>();

		try {			
			
			FDRODSRequestMessageCreator messageCreator = (FDRODSRequestMessageCreator)getMessageCreator();
			messageCreator.setQuery(query, params.toArray());
			String response = sendAndReceive(1, Boolean.FALSE);
			list.add(response);
		} catch (Exception e) {
			log.error("Oh Crap!", e);
		}

		// De"serialize" and add rows to message or error out.

		return list;
	}
	
}