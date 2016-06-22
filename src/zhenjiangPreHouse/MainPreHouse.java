package zhenjiangPreHouse;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;


import org.dom4j.DocumentException;



public class MainPreHouse {
	
	@SuppressWarnings("unused")
	private static boolean send_flag = false;
	
	/*ÿ�쳢��10��*/
	private static int times = 10;
	
	/*���Լ��*/
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
		
		/*���öԷ���Webservice����ѯ����˻��б�*/
		try {
			byte2 = new ZJClient().listAccountCall();
		} catch (Exception e) {
			// TODO: handle exception
			zjLog.log("��ѯ����˻��б�listAccount�쳣" + e);
			e.printStackTrace();
			return false;
		}
			
		/*����*/
		byte[] byte3 = null;
		byte3 = new ByteEncryptDecrypt().decrypt(byte2);
		
		if (byte3 == null || byte3.length == 0) {
			zjLog.log("�������޼���˻���Ϣ");
			return true;
		}
		
		String xml1 = null;
		try {
			xml1 = new String(byte3, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			zjLog.log("byteתxml�쳣" + e);
			e.printStackTrace();
			return false;
		}
		
		//System.out.println(xml1);
		
		/*��YFSD�������*/
		ArrayList<ListAccountResponse> send_list = new ArrayList<ListAccountResponse>();
		
		try {
			send_list = new ParseXML().parseListAccountResponse(xml1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			zjLog.log("����listAccount����xml�쳣" + e);
			e.printStackTrace();
			return false;
		}

		/*�ֶ�β�ѯ������ϸ*/
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
			zjLog.log("��ѯ����ƽ̨������ˮ�쳣");
			e.printStackTrace();
			return false;
		}
		
		
		if (recv_list.size() == 0) {
			zjLog.log("����˺���ϸΪ�գ����ձ��ͽ���");
			return true;
		}
		
		/*���ݷ��ؽ����XML*/
		String send_xml = new BuildXML().buildSendXMLRequest(recv_list);
		
		byte[] byte4 = null;
		try {
			byte4 = send_xml.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			zjLog.log("sendXMLFile����xmlתbyte�쳣" + e);
			e.printStackTrace();
			return false;
		}
		
		byte[] byte5 = new ByteEncryptDecrypt().encrypt(byte4);
		
		String sendStateResult = "";
		try {
			sendStateResult = new ZJClient().sendXMLCall(byte5);
		} catch (Exception e) {
			// TODO: handle exception
			zjLog.log("���ͼ���˻���ˮsendXmlFile�쳣" + e);
			e.printStackTrace();
			return false;
		}
		
		System.out.println(sendStateResult);
		
		int return_code = new ParseXML().parseSendXmlFileResponse(sendStateResult);
		if (return_code != 0) {
			send_flag = false;
			zjLog.log("����ʧ�ܣ�������" + return_code + "����xml" + send_xml);
			return false;
		}
		else {
			zjLog.log("���ͳɹ�,��������" + send_xml);
			send_flag = true;
		}
		return true;
	}

}
