package zhenjiangPreHouse;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;


import org.dom4j.DocumentException;



public class MainPreHouse {
	
	@SuppressWarnings("unused")
	private static boolean send_flag = false;
	
	/*每天尝试10次*/
	private static int times = 10;
	
	/*尝试间隔*/
	private static int delay = 10000;

	public static void main(String[] args) throws DocumentException, InterruptedException {
		// TODO Auto-generated method stub	
		boolean status = false;
		int time = 0;
		do {
			status = mainFlow();
			time++;
			Thread.currentThread();
			Thread.sleep(delay);
		} while (status == false && time < times );
	}
	
	private static boolean mainFlow() throws DocumentException{
		ZJLog zjLog = new ZJLog();
		
		byte[] byte2 = null;
		
		/*调用对方的Webservice，查询监管账户列表*/
		try {
			byte2 = new ZJClient().listAccountCall();
		} catch (Exception e) {
			// TODO: handle exception
			zjLog.log("查询监管账户列表listAccount异常" + e);
			e.printStackTrace();
			return false;
		}
			
		/*解密*/
		byte[] byte3 = null;
		byte3 = new ByteEncryptDecrypt().decrypt(byte2);
		
		if (byte3 == null || byte3.length == 0) {
			zjLog.log("第三方无监管账户信息");
			return true;
		}
		
		String xml1 = null;
		try {
			xml1 = new String(byte3, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			zjLog.log("byte转xml异常" + e);
			e.printStackTrace();
			return false;
		}
		
		//System.out.println(xml1);
		
		/*组YFSD请求对象*/
		ArrayList<ListAccountResponse> send_list = new ArrayList<ListAccountResponse>();
		
		try {
			send_list = new ParseXML().parseListAccountResponse(xml1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			zjLog.log("解析listAccount返回xml异常" + e);
			e.printStackTrace();
			return false;
		}

		/*分多次查询交易明细*/
		ArrayList<SendXmlFileRequest> recv_list = new ArrayList<SendXmlFileRequest>();
		
		YFSDClient yfsdClient = new YFSDClient();
		try {
			for (int i = 0; i < send_list.size(); i++) {
				ArrayList<SendXmlFileRequest> tmp_list;
				tmp_list = yfsdClient.callYFSDOnce(send_list.get(i));
				for (int j = 0; j < tmp_list.size(); j++) {
					recv_list.add(tmp_list.get(j));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			zjLog.log("查询考核平台交易流水异常");
			e.printStackTrace();
			return false;
		}
		
		
		if (recv_list.size() == 0) {
			zjLog.log("监管账号明细为空，本日报送结束");
			return true;
		}
		
		/*根据返回结果组XML*/
		String send_xml = new BuildXML().buildSendXMLRequest(recv_list);
		
		byte[] byte4 = null;
		try {
			byte4 = send_xml.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			zjLog.log("sendXMLFile输入xml转byte异常" + e);
			e.printStackTrace();
			return false;
		}
		
		byte[] byte5 = new ByteEncryptDecrypt().encrypt(byte4);
		
		String sendStateResult = "";
		try {
			sendStateResult = new ZJClient().sendXMLCall(byte5);
		} catch (Exception e) {
			// TODO: handle exception
			zjLog.log("发送监管账户流水sendXmlFile异常" + e);
			e.printStackTrace();
			return false;
		}
		
		System.out.println(sendStateResult);
		
		int return_code = new ParseXML().parseSendXmlFileResponse(sendStateResult);
		if (return_code != 0) {
			send_flag = false;
			zjLog.log("发送失败，错误码" + return_code + "发送xml" + send_xml);
			return false;
		}
		else {
			zjLog.log("发送成功,发送内容" + send_xml);
			send_flag = true;
		}
		return true;
	}

}
