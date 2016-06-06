package zhenjiangPreHouse;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class ParseXML {
	public void parseListAccountResponse(String listAccountXML) throws DocumentException{

		
		Document doc = DocumentHelper.parseText(listAccountXML);
		
		Element root = doc.getRootElement();
		
		Iterator<Element> iter = root.elementIterator("RECORD");
		
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
		}
		
	}
}
