package zhenjiangPreHouse;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dom4j.DocumentException;

import cn.com.just.webservice.BankAuth;
import zjjg_webservice.AccountList;
import zjjg_webservice.TranData;

public class MainPrhHouse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			/*≤‚ ‘key*/
			StringBuffer ret_msg = new StringBuffer();
			System.out.println(ret_msg+"1233321");
			
			/* test git*/
			/* test git1 */
			/* test dev branch */

			ResultSet rs = null;
			String sql = "select * from dbo.t_cmbc_account_info";
			DBOperation dbOperation = new DBOperation();
			
			String key = "";

			/*≤‚ ‘bankAuth*/
			BankAuth bankAuth = new BankAuth();
			
			ArrayList<HashMap<String, Object>> list = dbOperation.SQLQuery(sql, rs, ret_msg);
			
			if (!ret_msg.toString().equals(dbOperation.getSUCCESS())) {
				System.err.println(ret_msg+"111");
				return;
			}
			
			HashMap<String, Object> map = list.get(0);
			
			Set set = map.entrySet();
			
			java.util.Iterator iterator = set.iterator();
			
			while (iterator.hasNext()) {
				Map.Entry mapentry = (Map.Entry) iterator.next();
				if (mapentry.getKey().equals("sup_key")) {
					key = mapentry.getValue().toString();
				}
				if (mapentry.getKey().equals("sup_authid")) {
					bankAuth.setAuName(mapentry.getValue().toString());
				}
				if (mapentry.getKey().equals("sup_passwd")) {
					bankAuth.setAuPwd(mapentry.getValue().toString());
				}
				System.out.println(mapentry.getKey() + "/" + mapentry.getValue());
			}
			
			System.out.println("1234");
			
			
			bankAuth.setAuName("Z");
			bankAuth.setAuPwd("1");
			
			byte[] byte2 = null;
			
			byte2 = new ZJClient().listAccountCall(bankAuth);
		
			byte[] byte3 = null;
			byte3 = new ByteEncryptDecrypt().decrypt(byte2, key);
			
			String xml1 = null;
			try {
				xml1 = new String(byte3, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(xml1);
			try {
				new ParseXML().parseListAccountResponse(xml1);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayList<AccountList> accountList = new ArrayList<AccountList>();
			AccountList accountList1 = new AccountList();
			AccountList accountList2 = new AccountList();
			
			accountList.add(accountList1);
			accountList.add(accountList2);
			
			ArrayList<TranData> tranDataList = null;
			tranDataList = new KHClient().tranDataCall(accountList, "2016-05-06");
			
			String sendXML = new BuildXML().buildSendXMLRequest(tranDataList);
			
			byte[] byte4 = null;
			try {
				byte4 = sendXML.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte[] byte5 = new ByteEncryptDecrypt().encrypt(byte4, key);
			
			String sendStateResult = new ZJClient().sendXMLCall(byte5, bankAuth);
			
			System.out.println(sendStateResult);
	}

}
