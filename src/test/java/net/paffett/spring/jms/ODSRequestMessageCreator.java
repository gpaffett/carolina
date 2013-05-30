package net.paffett.spring.jms;

import net.paffett.domain.ods.request.FDRRequestMessage;

public class ODSRequestMessageCreator extends XStreamMessageCreator {

	public ODSRequestMessageCreator(FDRRequestMessage odsRequestMessage, JMSHeader header) {
		super(odsRequestMessage, header);
	}

}
