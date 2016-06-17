package zhenjiangPreHouse;

public class ListAccountResponse {
	/*监管账号*/
	private String acID;
	
	/*监管账号名称*/
	private String acName;
	
	/*开发公司名称*/
	private String prName;
	
	/*监管银行*/
	private String baName;

	public String getAcID() {
		return acID;
	}

	public void setAcID(String acID) {
		this.acID = acID;
	}

	public String getAcName() {
		return acName;
	}

	public void setAcName(String acName) {
		this.acName = acName;
	}

	public String getPrName() {
		return prName;
	}

	public void setPrName(String prName) {
		this.prName = prName;
	}

	public String getBaName() {
		return baName;
	}

	public void setBaName(String baName) {
		this.baName = baName;
	}
	
	
}
