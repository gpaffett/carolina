package net.paffett.domain.ods.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.basic.NullConverter;

@XStreamAlias("SECURITY")
public class ODSSecurityElement {
	
	@XStreamAlias("USERID")
	private String userId;
	
	@XStreamAlias("PASSWD")
	private String password = "";
	
	@XStreamAlias("PASSWDHASH")
	private String passwordHash;
	
	@XStreamAlias("ENCRYPTCODEPG")
	private String encryptCodePage;
	
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
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getEncryptCodePage() {
		return encryptCodePage;
	}
	public void setEncryptCodePage(String encryptCodePage) {
		this.encryptCodePage = encryptCodePage;
	}
	@Override
	public String toString() {
		return "ODSSecurityElement [userId=" + userId + ", password="
				+ password + ", passwordHash=" + passwordHash
				+ ", encryptCodePage=" + encryptCodePage + "]";
	}
	
}
