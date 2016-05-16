package main.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {
	public static final String LOG_FILE=System.getProperty("user.dir")+"/log/log2016";
	
	private PrintWriter logWriter;
	public Log(){
		try {
			logWriter=new PrintWriter(new FileWriter(new File(Log.LOG_FILE),true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void log(String msg){
		logWriter.println(msg);
		logWriter.close();
	}
	
	public static void main(String args[]){
		Log log=new Log();
		log.log("i am shenguojun");
	}
}
