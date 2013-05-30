package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("MESSAGE")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"messageText"})
public class ODSMessageElement {
	
	@XStreamAlias("RC")
	@XStreamAsAttribute
	private String ReplyCode;
	
	@XStreamAlias("SEVERITY")
	@XStreamAsAttribute
	private String severity;
	
	private String messageText;

	public String getReplyCode() {
		return ReplyCode;
	}

	public void setReplyCode(String replyCode) {
		ReplyCode = replyCode;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getmessageText() {
		return messageText;
	}

	public void setmessageText(String messageText) {
		this.messageText = messageText;
	}

	@Override
	public String toString() {
		return "ODSMessageElement [ReplyCode=" + ReplyCode + ", severity="
				+ severity + ", messageText=" + messageText + "]";
	}
		
}
