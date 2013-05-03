package net.paffett.spring.jms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class ActivationCodeInquiry extends FDRODSMessageHandler {
	
	private static Boolean RETRY = Boolean.TRUE;
	private static int RETRY_COUNT = 0;
	
	private static String query = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = \"{0}\"";
	
	public ActivationCodeInquiry () {
		super(query);
	}
		
	public List<?> execute(List<?> params) {
		List<String> list = new ArrayList<String>();

		try {			
			
				
//			messageCreator.setMessageText(, formatMessageText(query, params.toArray()));			
			String response = sendAndReceive(messageCreator, RETRY_COUNT, RETRY);
			list.add(response);
			
		} catch (Exception e) {
			log.error("Oh Crap!", e);
		}

		// De"serialize" and add rows to message or error out.

		return list;
	}
	
}