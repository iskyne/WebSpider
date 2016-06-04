package main.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import main.context.Context;

public class FileLog extends AbstractLog {
	
	private String logPath;
	
	private PrintWriter logWriter;
	
	public FileLog(){
		this(INFO);
	}
	
	public FileLog(int level){
		setLogLevel(level);
		initialize();
	}
	
	/*
	 * return the current date string formatted "YYYYMMDD";
	 */
	private String getDateStr(){
		DateFormat format=DateFormat.getDateInstance();
		Date date=new Date(System.currentTimeMillis());
		String datetmp=format.format(date);
		String[] dateElements=datetmp.split("-");
		dateElements[1]=dateElements[1].length()==1?("0"+dateElements[1]):dateElements[1];
		dateElements[2]=dateElements[2].length()==1?("0"+dateElements[2]):dateElements[2];
		return new StringBuffer().
				append(dateElements[0]).
				append(dateElements[1]).
				append(dateElements[2]).
				toString();
	}
	
	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context=context;
	}
	
	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this.context;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		String dateStr=getDateStr();
		logPath=System.getProperty("user.dir")+"/log/log."+getDateStr();
		try {
			logWriter=new PrintWriter(new FileWriter(new File(logPath),true),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public synchronized void log(Throwable t, String msg) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * log in message in file
	 */
	@Override
	public synchronized void log(String msg){
		log(msg,INFO);
	}
	

	@Override
	public void log(String msg, int level) {
		// TODO Auto-generated method stub
		if(level>=logLevel){
			String firstLine=new StringBuffer(logLevelMsg[level]).
					append(new Date(System.currentTimeMillis()).toString()).
					append("------").
					toString();
			logWriter.println(firstLine);
			logWriter.println(msg);
		}
	}
	
	public static void main(String args[]){
		FileLog log=new FileLog(FileLog.DEBUG);
		log.log("hello world error",FileLog.ERROR);
		log.log("hello world warning",FileLog.WARNING);
		log.log("hello world debug",FileLog.DEBUG);
		log.log("hello world info",FileLog.INFO);
		
	}
}
