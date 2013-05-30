package net.paffett.spring.jms;

import java.util.Map;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;

public abstract class ODSQueryMessageHandler extends ODSMessageHandler {

	public ODSQueryMessageHandler(String query) {
		super(query);
	}
	
	@Override
	/**
	 * 
	 */
	protected FDRRequestMessage createODSMessage(String query) {

		FDRRequestMessage fdrRequest = new FDRRequestMessage();
		ODSRequestElement odsRequest = new ODSRequestElement();
		
		odsRequest.setSelect(query);
		odsRequest.setColomnId(Boolean.FALSE);
		odsRequest.setRowDef(Boolean.TRUE);
		
		fdrRequest.setSecurityElement(getOdsSecurity());
		fdrRequest.addODSRequestElement(odsRequest);
		
		fdrRequest.setReplyTo(getReplyTo());

		return fdrRequest;
	}

}
