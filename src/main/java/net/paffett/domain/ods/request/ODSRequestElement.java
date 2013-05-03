package net.paffett.domain.ods.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.basic.BooleanConverter;

@XStreamAlias("ODSREQUEST")
public class ODSRequestElement {
	
	@XStreamAlias("ROWDEF")
	@XStreamConverter(value=BooleanConverter.class, booleans={true}, strings={"Y", "N"})
	private Boolean rowDef = Boolean.TRUE;
	
	@XStreamAlias("COLUMNID")
	@XStreamConverter(value=BooleanConverter.class, booleans={true}, strings={"Y", "N"})
	private Boolean colomnId;
	
	@XStreamAlias("USERDATA")
	private String userData;
	
	@XStreamAlias("SELECT")
	private String select;
	
	@XStreamAlias("EXEC")
	private String exec;
	
	public Boolean getRowDef() {
		return rowDef;
	}
	public void setRowDef(Boolean rowDef) {
		this.rowDef = rowDef;
	}
	public Boolean getColomnId() {
		return colomnId;
	}
	public void setColomnId(Boolean colomnId) {
		this.colomnId = colomnId;
	}
	public String getUserData() {
		return userData;
	}
	public void setUserData(String userData) {
		this.userData = userData;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getExec() {
		return exec;
	}
	public void setExec(String exec) {
		this.exec = exec;
	}
	
}
