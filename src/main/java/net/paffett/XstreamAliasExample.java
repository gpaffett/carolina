package net.paffett;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import net.paffett.domain.FDRRequestMessage;
import net.paffett.domain.ODSRequestElement;
import net.paffett.domain.ODSSecurityElement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class XstreamAliasExample {

	public static void main(String[] args) throws IOException {
				
		ODSSecurityElement security = new ODSSecurityElement();		
		security.setUserId("AAA");
		security.setPasswd("BBBBBB");
		
		ODSRequestElement request1 = new ODSRequestElement();
		request1.setRowDef(Boolean.TRUE);
		request1.setUserData("ABC");
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

}
