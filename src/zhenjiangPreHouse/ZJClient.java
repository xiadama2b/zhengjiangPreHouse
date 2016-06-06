package zhenjiangPreHouse;

import cn.com.just.webservice.BankAuth;
import cn.com.just.webservice.impl.*;

public class ZJClient {
	public byte[] listAccountCall(BankAuth bankAuth){
		IDataReportImplService service = new IDataReportImplService();
		DataReport dataReport = service.getIDataReportImplPort();
		byte[] byte1 = null;
		byte1 = dataReport.listAccount(bankAuth);
		return byte1;
	}
	
	public String sendXMLCall(byte[] xmlFile, BankAuth auth){
		IDataReportImplService service = new IDataReportImplService();
		DataReport dataReport = service.getIDataReportImplPort();
		String sendStateResult;
		sendStateResult = dataReport.sendXmlFlie(xmlFile, auth);
		return sendStateResult;
	}
}
