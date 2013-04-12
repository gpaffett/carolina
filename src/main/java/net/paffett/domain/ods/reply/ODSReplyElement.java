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
		
}
