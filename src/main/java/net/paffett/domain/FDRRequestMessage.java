package net.paffett.domain;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FDR")
public class FDRRequestMessage extends FDRMessage {

	@XStreamAlias("SECURITY")
	private ODSSecurityElement securityElement;

	@XStreamAlias("ODSREQUEST")
	@XStreamImplicit()
	private List<ODSRequestElement> requestElements;

	public ODSSecurityElement getSecurityElement() {
		return securityElement;
	}

	public void setSecurityElement(ODSSecurityElement securityElement) {
		this.securityElement = securityElement;
	}

	public List<ODSRequestElement> getRequestElements() {
		return requestElements;
	}

	public void addRequestElement(ODSRequestElement requestElement) {

		if (this.requestElements == null) {
			this.requestElements = new ArrayList<ODSRequestElement>();
		}

		this.requestElements.add(requestElement);
	}

}
