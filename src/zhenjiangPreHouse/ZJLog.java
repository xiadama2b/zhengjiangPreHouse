package zhenjiangPreHouse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class ZJLog {
	private static String logPath = "C:\\zjlog";
	
	private final static String enter = System.getProperty("line.separator");
	
	public void log(String logMessage){
		try {
			File folder = new File(logPath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			
			File file = new File(logPath + "\\" + this.getCurDate() + ".log");
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedReader in = new BufferedReader(new FileReader(file));
			String str = "";
	        String strToal = "";
	        while ((str = in.readLine()) != null)
	        {
	            strToal += (str + enter);
	        }
	        
	        strToal = strToal + this.getCurTime() + " " + logMessage + enter;
	        in.close();
	        BufferedWriter out = new BufferedWriter(new FileWriter(file));
	        out.write(strToal);
	        out.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	}
	
	private String getCurDate(){		
		Calendar calendar = Calendar.getInstance();
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		
		return year + (month < 10?"-0":"-") + month + (date < 10?"-0":"-") + date;
	}
	
	private String getCurTime(){
		Calendar calendar = Calendar.getInstance();

		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		return (hour < 10?"0":"") + hour + (minute < 10?":0":":") + minute + (second < 10?":0":":") + second;
	}
}
