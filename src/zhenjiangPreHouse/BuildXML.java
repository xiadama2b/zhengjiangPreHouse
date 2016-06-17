package zhenjiangPreHouse;

import java.util.ArrayList;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class BuildXML {
	public String buildSendXMLRequest(ArrayList<SendXmlFileRequest> list){
		Document doc = DocumentHelper.createDocument();
		Element zjjg = doc.addElement("ZJJG");
		Element mx = zjjg.addElement("MX");
		//Element record[] = null;
		for (int i = 0; i < list.size(); i++) {
			Element record = mx.addElement("RECORD");
			record.addElement("SEID").setText(list.get(i).getSeID());;
			record.addElement("ACID").setText(list.get(i).getAcID());
			record.addElement("ACNAME").setText(list.get(i).getAcName());
			record.addElement("COREGID").setText(list.get(i).getCoRegID());
			record.addElement("BUYERNAME").setText(list.get(i).getBuyerName());
			record.addElement("BARECORDDATE").setText(list.get(i).getBaRecordDate());
			record.addElement("DEALDATE").setText(list.get(i).getDealDate());
			record.addElement("DEALBA").setText(list.get(i).getDealBa());
			record.addElement("DEALADDR").setText(list.get(i).getDealAddr());
			record.addElement("DEALMODE").setText(list.get(i).getDealMode());
			record.addElement("DEALTYPE").setText(list.get(i).getDealType());
			record.addElement("OUTMONEY").setText(String.format("%.2f", list.get(i).getOutMoney()));
			record.addElement("INMONEY").setText(String.format("%.2f", list.get(i).getInMoney()));
			record.addElement("LEFTMONEY").setText(String.format("%.2f", list.get(i).getLeftmoney()));
			record.addElement("OUTACID").setText(list.get(i).getOutacID());
			record.addElement("OUTACNAME").setText(list.get(i).getOutacName());
			record.addElement("OUTBANAME").setText(list.get(i).getOutbaName());
			record.addElement("DEALNOTE").setText(list.get(i).getDealNote());
		}
		
		System.out.println(doc.asXML());
		return doc.asXML();
	}
}
