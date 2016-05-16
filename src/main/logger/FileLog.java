package main.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import main.context.Context;

public class FileLog implements Log {
	
	private Context context;
	
	private String logPath;
	
	private PrintWriter logWriter;
	
	private static class LogHolder{
		private static final FileLog log=new FileLog();
	}
	
	public static Log getInstance(){
		return LogHolder.log;
	}
	private FileLog(){
		String dateStr=getDateStr();
		logPath=System.getProperty("user.dir")+"/log/log."+getDateStr();
		try {
			logWriter=new PrintWriter(new FileWriter(new File(logPath),true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		StackTraceElement[] elements=Thread.currentThread().getStackTrace();
		StackTraceElement element=elements[elements.length-1];
		String firstLine=new StringBuffer(new Date(System.currentTimeMillis()).toString()).
				append("----- Class Name :").
				append(element.getClassName()).
				append("----- Method Name :").
				append(element.getMethodName()).
				append("------").
				toString();
		logWriter.println(firstLine);
		logWriter.println(msg);
		logWriter.close();
	}
	
	public static void main(String args[]){
		Log log=FileLog.getInstance();
		log.log("hello world");
	}
}
