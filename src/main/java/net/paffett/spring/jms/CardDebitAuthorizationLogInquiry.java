package net.paffett.spring.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CardDebitAuthorizationLogInquiry extends ODSMessageHandler {

	private static Boolean RETRY = Boolean.TRUE;
	private static int RETRY_COUNT = 0;

	private static String query = "ASELECT tran_cd, auth_dt, auth_tm, auth_am, appr_cd, befr_auth_avlb_crdt_am, " +
  		                               "dcln_resn_cd, mrch_ctgr_cd, mrch_city_nm, mrch_ctry_cd, mrch_nm, mrch_nr, " +
  		                               "mrch_pstl_cd, mrch_st_cd, mrch_zip_cd, ldgr_bal_am, totl_otst_dpst_am, " +
  		                               "dbt_avlb_fnds_am, totl_otst_dbt_auth_am, auth_prcs_tran_cd, totl_otst_prth_am, " +
  		                               "pin_prcs_cd, pin_vrfc_cd, net_dpst_am, entr_expr_dt, acqr_ica_bin_nr " +
  		                               "FROM CARDHOLDER_AUTHORIZATIONS WHERE ACCT_NR  = \"{0}\"";

	protected Log log = LogFactory.getLog(this.getClass());

	public CardDebitAuthorizationLogInquiry() {
		super(query);
	}

	public Map executeQuery(List<?> params) {

		Map<String, String> returnVal = new HashMap<String, String>();

		List<Map<String,String>> results = new ArrayList<Map<String,String>>();;
		
		try {
			results = sendAndReceive(params.toArray(), RETRY_COUNT, RETRY);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (null != results && results.size() > 0) {
			returnVal = (Map<String, String>)results.get(0);
		}
		
		return returnVal;

	}

	/**
	 * 
	 */
	protected AbstractMessageCreator createMessageCreator(String query) {

		JMSHeader header = new JMSHeader();
		FDRRequestMessage fdrRequest = new FDRRequestMessage();
		ODSRequestElement odsRequest = new ODSRequestElement();

		header.setReplyTo(getResponseDestination());

		odsRequest.setSelect(query);
		odsRequest.setColomnId(Boolean.FALSE);
		odsRequest.setRowDef(Boolean.TRUE);
		fdrRequest.setSecurityElement(getOdsSecurity());
		fdrRequest.addODSRequestElement(odsRequest);

		return new ODSRequestMessageCreator(fdrRequest, header);
	}

}