package net.paffett.spring.jms;

import javax.jms.Destination;

public class JMSHeader {

	private Destination replyTo;	
	private long   timeToLive;
	private String correlID;
	private String messageType;
	private long expiration;
	
	
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
	public Destination getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(Destination replyTo) {
		this.replyTo = replyTo;
	}

	public long getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}
	
	
	
}
