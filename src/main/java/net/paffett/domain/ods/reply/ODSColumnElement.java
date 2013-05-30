package net.paffett.domain.ods.reply;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("COLUMN")
public class ODSColumnElement {
		
	@XStreamAlias("ID")
	@XStreamAsAttribute
	private String id;
	
	@XStreamAlias("LEN")
	@XStreamAsAttribute
	private String length;
	
	@XStreamAlias("NULL")
	@XStreamAsAttribute
	private Boolean nullable;
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public Boolean getNullable() {
		return nullable;
	}
	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}
	
	@Override
	public String toString() {
		return "ODSColumnElement [id=" + id + ", length=" + length
				+ ", nullable=" + nullable + "]";
	}
	
}
