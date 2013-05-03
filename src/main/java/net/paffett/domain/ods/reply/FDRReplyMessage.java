package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

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

}
