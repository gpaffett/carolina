package net.paffett.domain.ods.request;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Destination;

import net.paffett.domain.FDRMessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("FDR")
public class FDRRequestMessage extends FDRMessage {

	@XStreamOmitField
	private Destination replyTo;
	
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

	public void addODSRequestElement(ODSRequestElement requestElement) {

		if (this.requestElements == null) {
			this.requestElements = new ArrayList<ODSRequestElement>();
		}

		this.requestElements.add(requestElement);
	}
	
	 public void addODSRequestElement(Boolean rowDef, Boolean columnId, String userData, String select )
	 {
		 ODSRequestElement odsRequestElement = new ODSRequestElement();
		 
		 odsRequestElement.setColomnId(columnId);		 
		 odsRequestElement.setRowDef(rowDef);
		 odsRequestElement.setSelect(select);
		 odsRequestElement.setUserData(userData);
		 		 
		 this.requestElements.add(odsRequestElement);
	 }
	
	public void setSecurityUserId(String userId) {
		securityElement.setUserId(userId);
	}
	
	public String getSecurityUserId() {
		return securityElement.getUserId();
	}

	public void setSecurityPassword(String passwd)	{
		securityElement.setPassword(passwd);
	}
	
	public String getSecurityPassword() {
		return securityElement.getPassword();
	}

	public Destination getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Destination replyTo) {
		this.replyTo = replyTo;
	}

	@Override
	public String toString() {
		return "FDRRequestMessage [replyTo=" + replyTo + ", securityElement="
				+ securityElement + ", requestElements=" + requestElements
				+ "]";
	}

}
