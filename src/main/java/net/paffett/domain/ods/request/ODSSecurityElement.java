package net.paffett.domain.ods.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SECURITY")
public class ODSSecurityElement {
	
	@XStreamAlias("USERID")
	private String userId;
	
	@XStreamAlias("PASSWD")
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
