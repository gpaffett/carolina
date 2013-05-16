package net.paffett.spring.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class ActivationCodeInquiry extends ODSMessageHandler {

	private static Boolean RETRY = Boolean.TRUE;
	private static int RETRY_COUNT = 0;

	private static String select = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = \"{0}\"";

	protected Log log = LogFactory.getLog(this.getClass());

	public ActivationCodeInquiry() {
		super(select);
	}

	public Map<String, String> executeQuery(List<?> params) {

		Map<String, String> returnVal = new HashMap<String, String>();

		List<Map<String,String>> results = new ArrayList<Map<String,String>>();;
		
		try {
			results = sendAndReceive(params.toArray(), RETRY_COUNT, RETRY);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != results && results.size() > 0) {
			returnVal = (Map<String, String>)results.get(0);
		}
		
		return returnVal;

	}

	/**
	 * 
	 */
	protected AbstractMessageCreator createMessageCreator(String query) {

		JMSHeader header = new JMSHeader();
		FDRRequestMessage fdrRequest = new FDRRequestMessage();
		ODSRequestElement odsRequest = new ODSRequestElement();

		header.setReplyTo(getResponseDestination());

		odsRequest.setSelect(query);
		odsRequest.setColomnId(Boolean.FALSE);
		odsRequest.setRowDef(Boolean.TRUE);
		fdrRequest.setSecurityElement(getOdsSecurity());
		fdrRequest.addODSRequestElement(odsRequest);

		return new ODSRequestMessageCreator(fdrRequest, header);
	}
	 
}