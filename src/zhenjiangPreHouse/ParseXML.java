package zhenjiangPreHouse;

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class ParseXML {
	private int success_code = 0;
	
	public ArrayList<ListAccountResponse> parseListAccountResponse(String listAccountXML) throws DocumentException{		
		Document doc = DocumentHelper.parseText(listAccountXML);
		
		Element root = doc.getRootElement();
		
		Iterator<Element> iter = root.elementIterator("RECORD");
		
		ArrayList<ListAccountResponse> list = new ArrayList<ListAccountResponse>();
				
		while (iter.hasNext()) {
			Element recordEle = (Element) iter.next();
			String acid = recordEle.elementTextTrim("ACID");
			String acname = recordEle.elementTextTrim("ACNAME");
			String prname = recordEle.elementTextTrim("PRNAME");
			String baname = recordEle.elementTextTrim("BANAME");
			System.out.println(acid);
			System.out.println(acname);
			System.out.println(prname);
			System.out.println(baname);
			
			ListAccountResponse listAccountResponse = new ListAccountResponse();
			
			listAccountResponse.setAcID(acid);
			listAccountResponse.setAcName(acname);
			listAccountResponse.setPrName(prname);
			listAccountResponse.setBaName(baname);
			
			list.add(listAccountResponse);
			
			listAccountResponse = null;
			
		}
		
		return list;
	}
	
	public int parseSendXmlFileResponse(String sendXmlFileXML) throws DocumentException{		
		Document doc = DocumentHelper.parseText(sendXmlFileXML);
		
		Element root = doc.getRootElement();
		
		Iterator<Element> iter = root.elementIterator("ERROR");
		
		String errorNumber = "";
				
		iter.hasNext();
		Element recordEle = (Element) iter.next();
		errorNumber = recordEle.elementTextTrim("ERRORNUMBER");
		
		return errorNumber.equals("")?this.success_code:Integer.parseInt(errorNumber);
	}
	

}
