package net.paffett;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class XstreamAliasExample {

	public static void main(String[] args) throws IOException {
				
		objectsToXML();
	
	}

	private static void objectsToXML() throws IOException {
		ODSSecurityElement security = new ODSSecurityElement();		
		security.setUserId("AAA");
		security.setPasswd("BBBBBB");
		
		ODSRequestElement request1 = new ODSRequestElement();
		request1.setRowDef(Boolean.TRUE);
		request1.setUserData("GEOFF");
		request1.setSelect("ADDRESS_1, ADDRESS_2, ZIP_POSTAL_CODE, ATV_CUR_BAL FROM ALPHA WHERE NAME=\"SMITH,JOHN P\" AND SYSTEM = 0220  AND LEVEL = \"L\" AND SEQ BETWEEN 1 AND 10 ");
		
		FDRRequestMessage fdrRequest = new FDRRequestMessage();
		fdrRequest.setSecurityElement(security);
		fdrRequest.addRequestElement(request1);
		
		XStream xstream  = new XStream();
		xstream.processAnnotations(FDRRequestMessage.class);
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		writer.write("<?xml version=\"1.0\" ?>\n");
		HierarchicalStreamWriter xmlWriter = new PrettyPrintWriter(writer);
		xstream.marshal(fdrRequest, xmlWriter);
		System.out.println( new String(stream.toByteArray()));
	}
	
	
	private static void xmlToObject() throws IOException {
		
		String xmlContent = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"0\"><USERDATA>ABC</USERDATA><ROWSET ROWS=\"2\">" +
		                    "<ROWDEF><COLUMN ID=\"ADDRESS_1\" LEN=\"26\" NULL=\"Y\"/><COLUMN ID=\"ADDRESS_2\" LEN=\"26\" NULL = \"Y\"/>" +
		                    "<COLUMN ID=\"ZIP_POSTAL_CODE\" LEN=\"10\" NULL=\"Y\"/><COLUMN ID=\"ATV_CUR_BAL\" LEN=\"4\" NULL=\"Y\"/>" +
		                    "</ROWDEF><ROW><C>10826 FARNAM</C><C>AK-12</C><C>68104-1000</C><C>99.23</C></ROW><ROW><C>7305 PACIFIC</C><C>AK-12</C>" +
		                    "<C>68104-1000</C><C>0.00</C></ROW></ROWSET></ODSREPLY></FDR>";


		XStream xstream  = new XStream();
		xstream.processAnnotations(FDRRequestMessage.class);
		
		
		
	}
	


}
