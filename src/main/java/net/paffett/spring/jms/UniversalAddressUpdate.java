package net.paffett.spring.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class UniversalAddressUpdate extends SpringSychronousMessageHandler {

	@Override
	protected MessageCreator getMessageCreator() {
		// TODO Auto-generated method stub
		return new UniversalAddressMessageCreator();
	}
	
	
	private class UniversalAddressMessageCreator extends FDRRequestMessageCreator {

		public Message createMessage(Session session) throws JMSException {

			textMessage = session.createTextMessage(message);

			if(null != jmsHeader && jmsHeader.getMessageType().length()>0){
				getTextMessage().setJMSType(jmsHeader.getMessageType());
			}

			if(null != jmsHeader && jmsHeader.getCorrelID().length()>0){
				getTextMessage().setJMSCorrelationID(jmsHeader.getCorrelID());
			}

			if(null != jmsHeader && jmsHeader.getExpiration()>0){
				getTextMessage().setJMSExpiration(jmsHeader.getExpiration());
			}

			return getTextMessage();
		}
	}
	 
}