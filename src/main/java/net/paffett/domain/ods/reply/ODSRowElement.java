package net.paffett.domain.ods.reply;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROW")
public class ODSRowElement {
	
	@XStreamAlias("C")
	@XStreamImplicit()
	private List<ODSColumnElement> columns;

	public List<ODSColumnElement> getColumns() {
		return columns;
	}

	public void setColumn(ODSColumnElement column) {
		if (this.columns == null) {
			this.columns = new ArrayList<ODSColumnElement>();
		}

		this.columns.add(column);
	}
	
	

}
