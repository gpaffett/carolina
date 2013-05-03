package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("MESSAGE")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"value"})
public class ODSMessageElement {
	
	@XStreamAlias("RC")
	@XStreamAsAttribute
	private String ReplyCode;
	
	@XStreamAlias("SEVERITY")
	@XStreamAsAttribute
	private String severity;
	
	private String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
		
}
