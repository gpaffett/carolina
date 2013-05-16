package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("FDR")
public class FDRReplyMessage {
	
	@XStreamAlias("ODSREPLY")
	private ODSReplyElement odsReplyElement;

	public ODSReplyElement getOdsReplyElement() {
		return odsReplyElement;
	}

	public void setOdsReplyElement(ODSReplyElement odsReplyElement) {
		this.odsReplyElement = odsReplyElement;
	}
	
	public String getResultCode()
	{
		return (this.odsReplyElement != null) ? odsReplyElement.getResultCode() : null; 		
	}
	
	public ODSMessageElement getMessage()
	{
		return (this.odsReplyElement != null) ? odsReplyElement.getMessage() : null; 		
	}
	
}
