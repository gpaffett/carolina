package net.paffett;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.paffett.domain.ods.reply.FDRReplyMessage;
import net.paffett.domain.ods.request.FDRRequestMessage;
import net.paffett.domain.ods.request.ODSRequestElement;
import net.paffett.domain.ods.request.ODSSecurityElement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class XstreamAliasExample {

	public static void main(String[] args) throws IOException {
				
		//objectsToXML();
		xmlToObject();
	
	}

	private static void objectsToXML() throws IOException {
		ODSSecurityElement security = new ODSSecurityElement();		
		security.setUserId("AAA");
		security.setPassword("BBBBBB");
		
		ODSRequestElement request1 = new ODSRequestElement();
		request1.setRowDef(Boolean.TRUE);
		request1.setUserData("GEOFF");
		request1.setSelect("ADDRESS_1, ADDRESS_2, ZIP_POSTAL_CODE, ATV_CUR_BAL FROM ALPHA WHERE NAME=\"SMITH,JOHN P\" AND SYSTEM = 0220  AND LEVEL = \"L\" AND SEQ BETWEEN 1 AND 10 ");
		
		FDRRequestMessage fdrRequest = new FDRRequestMessage();
		fdrRequest.setSecurityElement(security);
		fdrRequest.addODSRequestElement(request1);
		
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
		
		String xmlContentRowDef = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"0\"><ROWSET ROWS=\"2\">" +
		                    "<ROWDEF><COLUMN ID=\"ADDRESS_1\" LEN=\"26\" NULL=\"Y\"/><COLUMN ID=\"ADDRESS_2\" LEN=\"26\" NULL = \"Y\"/>" +
		                    "<COLUMN ID=\"ZIP_POSTAL_CODE\" LEN=\"10\" NULL=\"Y\"/><COLUMN ID=\"ATV_CUR_BAL\" LEN=\"4\" NULL=\"Y\"/>" +
		                    "</ROWDEF><ROW><C>10826 FARNAM</C><C>AK-12</C><C>68104-1000</C><C>99.23</C></ROW><ROW><C>7305 PACIFIC</C><C>AK-12</C>" +
		                    "<C>68104-1000</C><C>0.00</C></ROW></ROWSET></ODSREPLY></FDR>";
		
		String xmlContentColumnId = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"0\"><ROWSET ROWS=\"2\">" +
                "<ROW><C ID=\"ADDRESS_1\">10826 FARNAM</C><C ID=\"ADDRESS_2\">AK-12</C><C ID=\"ZIP_POSTAL_CODE\">68104-1000</C>" +
                "<C ID=\"ATV_CUR_BAL\">99.23</C></ROW><ROW><C ID=\"ADDRESS_1\">7305 PACIFIC</C><C ID=\"ADDRESS_2\">AK-12</C>" +
                "<C ID=\"ZIP_POSTAL_CODE\">68104-1000</C><C ID=\"ATV_CUR_BAL\">0.00</C></ROW></ROWSET></ODSREPLY></FDR>";

		String xmlContentNoBody = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"0\"/></FDR>";
		
		String xmlContentMessageBody = "<?xml version=\"1.0\"?><FDR version=\"1.0\"><ODSREPLY RC=\"298\">" + 
		                          "<MESSAGE RC=\"298\" SEVERITY=\"2\">** NO NAMES FOR THIS REQUEST **  VSCDSALF 01</MESSAGE></ODSREPLY></FDR>";
		

		XStream xstream  = new XStream();
		xstream.processAnnotations(FDRReplyMessage.class);
		
		FDRReplyMessage obj1 = (FDRReplyMessage)xstream.fromXML(xmlContentRowDef);
		
		FDRReplyMessage obj2 = (FDRReplyMessage)xstream.fromXML(xmlContentNoBody);
		
		FDRReplyMessage obj3 = (FDRReplyMessage)xstream.fromXML(xmlContentMessageBody);
		
		FDRReplyMessage obj4 = (FDRReplyMessage)xstream.fromXML(xmlContentColumnId);
		
	}
	


}
