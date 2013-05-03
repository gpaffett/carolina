package net.paffett.spring.jms;

import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

public class FDRSelectODSMessageCreator extends FDRRequestMessageCreator {
	
	 public void addODSRequest(Boolean rowDef, Boolean columnId, String select )
	 {
		 ODSRequestElement odsRequestElement = new ODSRequestElement();
		 
		 odsRequestElement.setColomnId(columnId);		 
		 odsRequestElement.setRowDef(rowDef);
		 odsRequestElement.setSelect(select);
		 		 
		 fdrRequest.addRequestElement(odsRequestElement);
	 }
	 
	public Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(message));

		if (null != header && header.getMessageType().length() > 0) {
			getTextMessage().setJMSType(header.getMessageType());
		}

		if (null != header && header.getCorrelID().length() > 0) {
			getTextMessage().setJMSCorrelationID(header.getCorrelID());
		}

		if (null != header && header.getExpiration() > 0) {
			getTextMessage().setJMSExpiration(header.getExpiration());
		}

		if (null != header && null != header.getReplyTo()
				&& null != header.getReplyTo()) {
			getTextMessage().setJMSReplyTo(header.getReplyTo());
		}

		return getTextMessage();
	}
		
}
