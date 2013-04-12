package net.paffett.domain.ods.reply;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ROWSET")
public class ODSRowSetElement {
	
	@XStreamAlias("ROWS")
	@XStreamAsAttribute
	private String rowCount;
	
	@XStreamAlias("ROW")
	@XStreamImplicit()
	private List<ODSRowElement> rows;

	public String getRowCount() {
		return rowCount;
	}

	public void setRowCount(String rowCount) {
		this.rowCount = rowCount;
	}

	public List<ODSRowElement> getRow() {
		return rows;
	}

	public void setRow(ODSRowElement row) {
		if (this.rows == null) {
			this.rows = new ArrayList<ODSRowElement>();
		}

		this.rows.add(row);
	}
	
}