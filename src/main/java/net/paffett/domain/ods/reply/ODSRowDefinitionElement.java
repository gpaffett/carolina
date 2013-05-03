package net.paffett.domain.ods.reply;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROWDEF")
public class ODSRowDefinitionElement {
	
	@XStreamAlias("COLUMN")
	@XStreamImplicit
	private List<ODSColumnElement> coulmns;

	public List<ODSColumnElement> getCoulmns() {
		return coulmns;
	}

	public void setCoulmns(List<ODSColumnElement> coulmns) {
		this.coulmns = coulmns;
	}

}
