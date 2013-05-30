package net.paffett.domain.ods.reply;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROWDEF")
public class ODSRowDefinitionElement {
	
	@XStreamAlias("COLUMN")
	@XStreamImplicit
	private List<ODSColumnElement> columns;

	public List<ODSColumnElement> getColumns() {
		return columns;
	}

	public void setColumns(List<ODSColumnElement> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "ODSRowDefinitionElement [columns=" + columns + "]";
	}

}
