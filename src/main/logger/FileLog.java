package main.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLog implements Log{
	public static final String LOG_FILE=System.getProperty("user.dir")+"/log/log2016";
	
	private PrintWriter logWriter;
	public FileLog(){
		try {
			logWriter=new PrintWriter(new FileWriter(new File(FileLog.LOG_FILE),true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * log in message in file
	 */
	@Override
	public void log(String msg){
		logWriter.println(msg);
		logWriter.close();
	}
	
	public static void main(String args[]){
		FileLog log=new FileLog();
		log.log("i am shenguojun");
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void log(Throwable t, String msg) {
		// TODO Auto-generated method stub
		
	}
}
