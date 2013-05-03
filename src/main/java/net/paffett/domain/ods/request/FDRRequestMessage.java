package net.paffett.domain.ods.request;

import java.util.ArrayList;
import java.util.List;

import net.paffett.domain.FDRMessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("FDR")
public class FDRRequestMessage extends FDRMessage {

	@XStreamAlias("SECURITY")
	private ODSSecurityElement securityElement = new ODSSecurityElement();

	@XStreamAlias("ODSREQUEST")
	@XStreamImplicit()
	private List<ODSRequestElement> requestElements = new ArrayList<ODSRequestElement>();

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
	
	public void setSecurityUserId(String userId) {
		securityElement.setUserId(userId);
	}
	
	public String getSecurityUserId() {
		return securityElement.getUserId();
	}

	public void setSecurityPassword(String passwd)	{
		securityElement.setPasswd(passwd);
	}
	
	public String getSecurityPassword() {
		return securityElement.getPasswd();
	}

}
