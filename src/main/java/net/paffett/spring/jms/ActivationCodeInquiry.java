package net.paffett.spring.jms;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActivationCodeInquiry extends ODSMessageHandler {

	private static Boolean RETRY = Boolean.TRUE;
	private static int RETRY_COUNT = 0;

	private static String query = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = \"{0}\"";

	protected Log log = LogFactory.getLog(this.getClass());

	public ActivationCodeInquiry() {
		super(query);
	}

	public Boolean executeQuery(List<?> params) {

		Boolean returnVal = Boolean.FALSE;

		List<Boolean> results;
		results = sendAndReceive(params.toArray(), RETRY_COUNT, RETRY);

		if (null != results && results.size() > 0) {
			returnVal = results.get(0);
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

		header.setReplyTo(getRequestDestination());

		odsRequest.setSelect(query);
		odsRequest.setColomnId(Boolean.FALSE);
		odsRequest.setRowDef(Boolean.TRUE);
		fdrRequest.setSecurityElement(getOdsSecurity());
		fdrRequest.addODSRequestElement(odsRequest);

		return new ODSRequestMessageCreator(fdrRequest, header);
	}

}