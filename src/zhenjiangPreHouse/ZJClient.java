package zhenjiangPreHouse;

import cn.com.just.webservice.BankAuth;
import cn.com.just.webservice.impl.*;

public class ZJClient {
	private BankAuth bankAuth;
	
	private String auName = "Z";
			
	private String auPwd = "1";
	
	private BankAuth bankAuthInit(){
		this.bankAuth = new BankAuth();
		this.bankAuth.setAuName(this.auName);
		this.bankAuth.setAuPwd(this.auPwd);
		return this.bankAuth;
	}
	
	public byte[] listAccountCall(){
		IDataReportImplService service = new IDataReportImplService();
		DataReport dataReport = service.getIDataReportImplPort();
		byte[] byte1 = null;
		byte1 = dataReport.listAccount(this.bankAuthInit());
		return byte1;
	}
	
	public String sendXMLCall(byte[] xmlFile){
		IDataReportImplService service = new IDataReportImplService();
		DataReport dataReport = service.getIDataReportImplPort();
		String sendStateResult;
		sendStateResult = dataReport.sendXmlFlie(xmlFile, this.bankAuthInit());
		return sendStateResult;
	}
}
