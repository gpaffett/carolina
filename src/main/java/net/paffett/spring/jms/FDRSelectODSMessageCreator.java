package net.paffett.spring.jms;

import java.util.Map;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

public class FDRSelectODSMessageCreator extends FDRRequestMessageCreator {

	private String message;
	private TextMessage textMessage;
	private Map<String, String> options = null;
	private JMSHeaderVO jmsHeaderVO = null;

	public void setMessage(String message) {
		this.message = message;
	}

	@SuppressWarnings("unused")
	public void setOptions(Map<String, String> options) {
		this.options = options;
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

	public JMSHeaderVO getJmsHeaderVO() {
		return jmsHeaderVO;
	}

	public void setJmsHeaderVO(JMSHeaderVO jmsHeaderVO) {
		this.jmsHeaderVO = jmsHeaderVO;
	}

	public Message createMessage(Session session) throws JMSException {

		setTextMessage(session.createTextMessage(message));

		if (null != jmsHeaderVO && jmsHeaderVO.getMessageType().length() > 0) {
			getTextMessage().setJMSType(jmsHeaderVO.getMessageType());
		}

		if (null != jmsHeaderVO && jmsHeaderVO.getCorrelID().length() > 0) {
			getTextMessage().setJMSCorrelationID(jmsHeaderVO.getCorrelID());
		}

		if (null != jmsHeaderVO && jmsHeaderVO.getExpiration() > 0) {
			getTextMessage().setJMSExpiration(jmsHeaderVO.getExpiration());
		}

		if (null != jmsHeaderVO && null != jmsHeaderVO.getReplyTo()
				&& null != jmsHeaderVO.getReplyTo()) {
			getTextMessage().setJMSReplyTo(jmsHeaderVO.getReplyTo());
		}

		if (null != options && options.size() > 0) {
			for (Entry<String, String> entry : options.entrySet()) {
				getTextMessage().setStringProperty(entry.getKey(),
						entry.getValue());
			}
		}
		return getTextMessage();
	}
}
