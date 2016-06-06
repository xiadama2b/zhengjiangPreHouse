package zhenjiangPreHouse;

import java.util.ArrayList;

import zjjg_webservice.AccountList;
import zjjg_webservice.TranData;
import zjjg_webservice.TranDataQry;
import zjjg_webservice.TranDataQryService;

public class KHClient {
	public ArrayList<TranData> tranDataCall(ArrayList<AccountList> accountList, String date){
		TranDataQryService service = new TranDataQryService();
		TranDataQry tranDataQry = service.getTranDataQryPort();
		ArrayList<TranData> tranDataList = new ArrayList<TranData>();
		tranDataList = (ArrayList<TranData>) tranDataQry.tranDataQry(accountList, date);
		//tranData = tranDataQry.tranDataQry(accountList, date);
		return tranDataList;
	}
}
