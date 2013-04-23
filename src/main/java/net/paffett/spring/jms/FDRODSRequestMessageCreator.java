package net.paffett.spring.jms;

import java.util.Map;
import java.util.Map.Entry;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class FDRODSRequestMessageCreator implements MessageCreator {
	private String message;
	private TextMessage textMessage;
	private JMSHeader jmsHeader= null;


	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageId() throws Exception {
		return textMessage.getJMSMessageID();
	}

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public JMSHeader getJmsHeaderVO() {
		return jmsHeader;
	}

	public void setJmsHeaderVO(JMSHeader jmsHeaderVO) {
		this.jmsHeader = jmsHeaderVO;
	}

	public javax.jms.Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(message)) ;

		if(null != jmsHeader && jmsHeader.getMessageType().length()>0){
			getTextMessage().setJMSType(jmsHeader.getMessageType());
		}

		if(null != jmsHeader && jmsHeader.getCorrelID().length()>0){
			getTextMessage().setJMSCorrelationID(jmsHeader.getCorrelID());
		}

		if(null != jmsHeader && jmsHeader.getExpiration()>0){
			getTextMessage().setJMSExpiration(jmsHeader.getExpiration());
		}
			
		if( null != jmsHeader && jmsHeader.getTimeToLive() > 0 ) {
			getJMSTemplate().setTimeToLive(jmsHeader.getTimeToLive());
		}else{
			getTextMessage().setJMSExpiration(0);
		}			

		if( null != jmsHeader && null != jmsHeader.getQueueMgrName() && null != jmsHeader.getQueueName()  ) {
			Destination destination = new com.ibm.mq.jms.MQQueue(jmsHeader.getQueueMgrName(), jmsHeader.getQueueName());
			getTextMessage().setJMSReplyTo(destination);
		}			

		return getTextMessage();
	}
}
