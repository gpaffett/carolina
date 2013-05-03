package net.paffett.domain.ods.reply;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROW")
public class ODSRowElement {
	
	@XStreamAlias("C")
	@XStreamImplicit()
	private List<ODSCElement> columns;

	public List<ODSCElement> getColumns() {
		return columns;
	}

	public void setColumn(ODSCElement column) {
		if (this.columns == null) {
			this.columns = new ArrayList<ODSCElement>();
		}

		this.columns.add(column);
	}
	
	

}
