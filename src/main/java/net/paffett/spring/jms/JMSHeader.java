package net.paffett.spring.jms;

public class JMSHeader {

	String queueMgrName;
	String queueName;
	long   timeToLive;
	String correlID;
	String messageType;
	long expiration;
	
	
	public String getCorrelID() {
		return correlID;
	}
	public void setCorrelID(String correlID) {
		this.correlID = correlID;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public long getExpiration() {
		return expiration;
	}
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	public String getQueueMgrName() {
		return queueMgrName;
	}
	public void setQueueMgrName(String queueMgrName) {
		this.queueMgrName = queueMgrName;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public long getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}
	
	
	
}
