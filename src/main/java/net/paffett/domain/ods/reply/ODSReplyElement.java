package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ODSREPLY")
public class ODSReplyElement {
	
	@XStreamAlias("RC")
	@XStreamAsAttribute
	private String resultCode;
	
	@XStreamAlias("USERDATA")
	private String userData;
	
	@XStreamAlias("ROWSET")	
	private ODSRowSetElement rowSet;
	
	@XStreamAlias("MESSAGE")	
	private ODSMessageElement message;
		
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public ODSRowSetElement getRowSet() {
		return rowSet;
	}
	
	public void setRowDef(ODSRowSetElement rowSet) {
		this.rowSet = rowSet;
	}
	
	public String getUserData() {
		return userData;
	}
	
	public void setUserData(String userData) {
		this.userData = userData;
	}

	public ODSMessageElement getMessage() {
		return message;
	}

	public void setMessage(ODSMessageElement message) {
		this.message = message;
	}

	public void setRowSet(ODSRowSetElement rowSet) {
		this.rowSet = rowSet;
	}

	@Override
	public String toString() {
		return "ODSReplyElement [resultCode=" + resultCode + ", userData="
				+ userData + ", rowSet=" + rowSet + ", message=" + message
				+ "]";
	}
		
}
