package zhenjiangPreHouse;

public class SendXmlFileRequest {
	/*交易流水号*/
	private String seID;
	/*监管账号*/
	private String acID;
	/*监管账号名称*/
	private String acName;
	/*合同编号*/
	private String coRegID;
	/*交款人*/
	private String buyerName;
	/*银行记账日期*/
	private String baRecordDate;
	/*交易时间*/
	private String dealDate;
	/*交易银行*/
	private String dealBa;
	/*交易地点*/
	private String dealAddr;
	/*交易方式 现金or转账*/
	private String dealMode;
	/*交易类型 收入or支出*/
	private String dealType;
	/*支出金额*/
	private double outMoney;
	/*收入金额*/
	private double inMoney;
	/*本次余额*/
	private double leftMoney;
	/*转出账户*/
	private String outacID;
	/*转出账户名称*/
	private String outacName;
	/*转出银行*/
	private String outbaName;
	/*交易说明*/
	private String dealNote;
	
	public String getSeID() {
		return seID;
	}
	public void setSeID(String seID) {
		this.seID = seID;
	}
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
	public String getCoRegID() {
		return coRegID;
	}
	public void setCoRegID(String coRegID) {
		this.coRegID = coRegID;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBaRecordDate() {
		return baRecordDate;
	}
	public void setBaRecordDate(String baRecordDate) {
		this.baRecordDate = baRecordDate;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getDealBa() {
		return dealBa;
	}
	public void setDealBa(String dealBa) {
		this.dealBa = dealBa;
	}
	public String getDealAddr() {
		return dealAddr;
	}
	public void setDealAddr(String dealAddr) {
		this.dealAddr = dealAddr;
	}
	public String getDealMode() {
		return dealMode;
	}
	public void setDealMode(String dealMode) {
		this.dealMode = dealMode;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public double getOutMoney() {
		return outMoney;
	}
	public void setOutMoney(double outMoney) {
		this.outMoney = outMoney;
	}
	public double getInMoney() {
		return inMoney;
	}
	public void setInMoney(double inMoney) {
		this.inMoney = inMoney;
	}
	public double getLeftMoney() {
		return leftMoney;
	}
	public void setLeftMonEy(double leftMoney) {
		this.leftMoney = leftMoney;
	}
	public String getOutacID() {
		return outacID;
	}
	public void setOutacID(String outacID) {
		this.outacID = outacID;
	}
	public String getOutacName() {
		return outacName;
	}
	public void setOutacName(String outacName) {
		this.outacName = outacName;
	}
	public String getOutbaName() {
		return outbaName;
	}
	public void setOutbaName(String outbaName) {
		this.outbaName = outbaName;
	}
	public String getDealNote() {
		return dealNote;
	}
	public void setDealNote(String dealNote) {
		this.dealNote = dealNote;
	}
	public SendXmlFileRequest(String seID, String acID, String acName, String coRegID, String buyerName,
			String baRecordDate, String dealDate, String dealBa, String dealAddr, String dealMode, String dealType,
			double outMoney, double inMoney, double leftMoney, String outacID, String outacName, String outbaName,
			String dealNote) {
		super();
		this.seID = seID;
		this.acID = acID;
		this.acName = acName;
		this.coRegID = coRegID;
		this.buyerName = buyerName;
		this.baRecordDate = baRecordDate;
		this.dealDate = dealDate;
		this.dealBa = dealBa;
		this.dealAddr = dealAddr;
		this.dealMode = dealMode;
		this.dealType = dealType;
		this.outMoney = outMoney;
		this.inMoney = inMoney;
		this.leftMoney = leftMoney;
		this.outacID = outacID;
		this.outacName = outacName;
		this.outbaName = outbaName;
		this.dealNote = dealNote;
	}
	
	
}
