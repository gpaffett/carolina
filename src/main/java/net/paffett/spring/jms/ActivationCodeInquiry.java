package net.paffett.spring.jms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ActivationCodeInquiry extends ODSQueryMessageHandler {

	private static Boolean RETRY = Boolean.TRUE;
	private static int RETRY_COUNT = 0;

	private static String select = "ATV_RIS_CTL_FLG FROM CHDHDLR WHERE CARDHDLR = \"{0}\"";

	protected Log log = LogFactory.getLog(this.getClass());

	public ActivationCodeInquiry() {
		super(select);
	}

	public Boolean executeQuery(List<?> params) {

		Boolean returnVal = Boolean.FALSE;
		List<Boolean> results = new ArrayList<Boolean>();;
		
		try {
			log.info("Sending and receiving Activation Inquiry message ");
			results = sendAndReceive(params.toArray(), RETRY_COUNT, RETRY);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			log.error("Failure in Activation Code Inquiry", e);
		}

		  if (null != results && results.size() > 0){
			  returnVal = results.get(0);
		  } 
		
		return returnVal;
	}
	
	protected Boolean mapRow(Map<String,String> results) {
		
		int atvRisCtlFlg;
	    
	    try 
	    {      
	      atvRisCtlFlg = Integer.parseInt(results.get("ATV_RIS_CTL_FLG"));
	    }
	    catch (NumberFormatException ex) 
	    {      
	      atvRisCtlFlg = 0;
	    }
	    
	    return (atvRisCtlFlg != 0) && (atvRisCtlFlg != 9);    
	    
	}
	 
}