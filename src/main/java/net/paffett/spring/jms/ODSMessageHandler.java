package net.paffett.spring.jms;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import net.paffett.domain.ods.reply.FDRReplyMessage;
import net.paffett.domain.ods.reply.ODSCElement;
import net.paffett.domain.ods.reply.ODSColumnElement;
import net.paffett.domain.ods.reply.ODSReplyElement;
import net.paffett.domain.ods.reply.ODSRowDefinitionElement;
import net.paffett.domain.ods.reply.ODSRowElement;
import net.paffett.domain.ods.reply.ODSRowSetElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author gp58112
 * 
 */
public abstract class ODSMessageHandler extends
		AbstractSpringSynchronousMessageHandler implements InitializingBean {

	protected Log log = LogFactory.getLog(this.getClass());
	
	private ODSSecurityElement odsSecurity;
	private Boolean rowDef = Boolean.TRUE;
	private Boolean columnId;
	private String query;

	public ODSMessageHandler(String query) {
		this.query = query;
	}

	public List<Map<String,String>> sendAndReceive(Object[] params, int retryAttempts,
			Boolean retry) throws JMSException, Exception {

		if (odsSecurity == null) {
			throw new IllegalArgumentException(
					"ODS Security Element cannot be null!");
		}

		AbstractMessageCreator messageCreator = createMessageCreator(MessageFormat
				.format(query, params));

		String messageId = sendMessage(messageCreator, getRequestDestination(),
				retryAttempts, retry);

		log.info("Message: " + messageId + " has been Sent");

		String responseString = receiveMessage(messageId,
				getResponseDestination(), JMS_RESPONSE_RETRY_COUNT);

		if (responseString == null) { 
			log.error("JMS TimeOut occurred while receiving response.");
			throw new JMSException(
					"JMS TimeOut occurred while receiving response.");
		}
		
		FDRReplyMessage reply = (FDRReplyMessage)formatResponse(responseString, FDRReplyMessage.class);
		
		return mapResponse(reply);
	}

	private Object formatResponse(String xmlString, Class<?> clazz) {
		XStream xstream = new XStream();
		xstream.processAnnotations(clazz);

		return xstream.fromXML(xmlString);
	}
	
	private List<Map<String,String>> mapResponse (FDRReplyMessage reply)
	{		
		ODSReplyElement odsReply = reply.getOdsReplyElement();
		ODSRowSetElement rowSet = odsReply.getRowSet();
		
		return isRowDefEnabled() ? mapByRowDef(rowSet) : mapByColumn(rowSet.getRow()); 		
	}
	
	private List<Map<String,String>> mapByRowDef(ODSRowSetElement rowSet)
	{
		List<ODSRowElement> rows = rowSet.getRow();
		ODSRowDefinitionElement rowDef = rowSet.getOdsRowDef();
		
		List<Map<String, String>> results = new ArrayList<Map<String, String>>(rows.size());
		
		for ( ODSRowElement row : rows ) {
			
			Map<String, String> map = new HashMap<String, String>();
			List<ODSColumnElement> columnMetadata = rowDef.getColumns();
			List<ODSCElement> dataColumns = row.getColumns();
			
			if (dataColumns.size() != columnMetadata.size() ) {
				throw new RuntimeException("Size of Column Metadata doesn't match number of coluns returned");
			}
			
			for ( int i = 0; i < dataColumns.size(); i++  )	{
				ODSColumnElement metadata = columnMetadata.get(i);
				ODSCElement data = dataColumns.get(i);				
				map.put(metadata.getId(), data.getValue());				
			}
			
		    results.add(map);
		}
		
	    return results;	
	}
	
	private List<Map<String,String>> mapByColumn(List<ODSRowElement> rows)
	{
		List<Map<String, String>> results = new ArrayList<Map<String, String>>(rows.size());
		
		for ( ODSRowElement row : rows ) {
			
			Map<String, String> map = new HashMap<String, String>();			
			List<ODSCElement> dataColumns = row.getColumns();
			
			for ( int i = 0; i < dataColumns.size(); i++  )	{				
				ODSCElement data = dataColumns.get(i);				
				map.put(data.getId(), data.getValue());				
			}
			
		    results.add(map);
		}
		
	    return results;	
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public ODSSecurityElement getOdsSecurity() {
		return odsSecurity;
	}

	public void setOdsSecurity(ODSSecurityElement odsSecurity) {
		this.odsSecurity = odsSecurity;
	}

	public Boolean isRowDefEnabled() {
		return rowDef;
	}

	public void setRowDef(Boolean rowDef) {
		this.rowDef = rowDef;
	}

	public Boolean isColumnIdEnabled() {
		return columnId == null ? Boolean.FALSE : columnId;
	}

	public void setColumnId(Boolean columnId) {
		this.columnId = columnId;
	}

	
}