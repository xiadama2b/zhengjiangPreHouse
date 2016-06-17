package zhenjiangPreHouse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YFSDClient {
	/*超时时间*/	
	private int time_out = 30000;
	
	/*服务端URL*/
	private String url = "http://198.25.0.47:8080/YFSD_NANJING/getZJJYMX";
	
	@SuppressWarnings("deprecation")
	public ArrayList<SendXmlFileRequest> callYFSDOnce(ListAccountResponse listAccountResponse){
		PostMethod method = new PostMethod(this.url);
		
		//RequestEntity re = new StringRequestEntity(tranJson, "application/json", "UTF-8"); 
		
		//method.setRequestEntity(re);
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, this.time_out);
        
        method.setRequestHeader("User-Agent", "Fiddler");
        
        method.setRequestBody(this.BuildRequestJson(listAccountResponse));
        
        HttpClient httpClient = new HttpClient();
        
        int statusCode = 0;
        
        ArrayList<SendXmlFileRequest> return_list = null;
		try {
			statusCode = httpClient.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("http status code " + statusCode);
				return null;
			}
			
			InputStream inStream = method.getResponseBodyAsStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			ArrayList<String> list = new ArrayList<String>();
			String line = "";
			//StringBuilder builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				list.add(line);
				line = "";
			}
			
			return_list = this.ParseResponseJson(list);
			
			method.releaseConnection();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return return_list;
	}
	
	/*
	 * get last date
	 * */
	private String getLastDate(){
		String lastDate = "";
		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);
		
		// 上个月31：1 2 4 6 8 9 11 上个月30天  5 7 10 12 山个月28 29天 3
		date = date - 1;
		if (date == 0) {
			if (month == 1 || month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11) {
				date = 31;
			}
			else if (month == 5 || month == 7 || month == 10 || month == 12) {
				date = 30;
			}
			else {
				if (year%4 == 0) {
					date = 29;
				}
				else {
					date = 28;
				}
			}
			
			month = month - 1;
		}
		
		if (month == 0) {
			month = 12;
			year = year - 1;
		}
		
		lastDate = year + "-" + month + "-" + date;
		return lastDate;
	}
	
	/*
	 *	Request Json Build 
	 * */
	
	private String BuildRequestJson(ListAccountResponse listAccountResponse){
		String tranJson = "";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dealDate", this.getLastDate());
		jsonObject.put("acID", listAccountResponse.getAcID());
		
		tranJson = jsonObject.toString();
		
		return tranJson;
	}


	/*
	 * parase response Json
	 * */

	private ArrayList<SendXmlFileRequest> ParseResponseJson(ArrayList<String> list){
		ArrayList<SendXmlFileRequest> return_list = new ArrayList<SendXmlFileRequest>();
		
		ArrayList<SendXmlFileRequest> tmp_list;
		
		for (int i = 0; i < list.size(); i++) {
			tmp_list = this.ParasOneJson(list.get(i));
			for (int j = 0; j < tmp_list.size(); j++) {
				return_list.add(tmp_list.get(j));
			}
			
			tmp_list = null;
		}
		
		return return_list;
	}
	
	private ArrayList<SendXmlFileRequest> ParasOneJson(String line){
		char head = line.charAt(0);
		char tail = line.charAt(line.length() - 1);
		
		if (head == '[' && tail == ']') {
			line = "{\"root\":" + line + "}";
		}
		
		@SuppressWarnings("deprecation")
		JSONObject jsonObject = new JSONObject(line);
		
		JSONArray jsonArray = jsonObject.getJSONArray("root");
		
		int count = jsonArray.length();
		
		String seID = "";
		String acID = "";
		String acName = "";
		String coRegID = "";
		String buyerName = "";
		String baRecordDate = "";
		String dealDate = "";
		String dealBa = "";
		String dealAddr = "";
		String dealMode = "";
		String dealType = "";
		double outMoney = 0.0;
		double inMoney = 0.0;
		double leftMoney = 0.0;
		String outacID = "";
		String outacName = "";
		String outbaName = "";
		String dealNote = "";
		
		ArrayList<SendXmlFileRequest> list = new ArrayList<SendXmlFileRequest>();
		for (int i = 0; i < count; i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			seID = jsonObj.getString("seID");
			acID = jsonObj.getString("acID");
			acName = jsonObj.getString("acName");
			coRegID = jsonObj.getString("coRegID");
			buyerName = jsonObj.getString("buyerName");
			baRecordDate = jsonObj.getString("baRecordDate");
			dealDate = jsonObj.getString("dealDate");
			dealBa = jsonObj.getString("dealBa");
			dealAddr = jsonObj.getString("dealAddr");
			dealMode = jsonObj.getString("dealMode");
			dealType = jsonObj.getString("dealType");
			outMoney = jsonObj.getDouble("outMoney");
			inMoney = jsonObj.getDouble("inMoney");
			leftMoney = jsonObj.getDouble("leftMoney");
			outacID = jsonObj.getString("outacID");
			outacName = jsonObj.getString("outacName");
			outbaName = jsonObj.getString("outbaName");
			dealNote = jsonObj.getString("dealNote");
			
			SendXmlFileRequest sendXmlFileRequest;
			sendXmlFileRequest = new SendXmlFileRequest(seID, acID, acName, coRegID, buyerName, baRecordDate, dealDate, dealBa, dealAddr, dealMode, dealType, outMoney, inMoney, leftMoney, outacID, outacName, outbaName, dealNote);
			
			list.add(sendXmlFileRequest);
			
			sendXmlFileRequest = null;
		}
		
		return list;
	}
}
