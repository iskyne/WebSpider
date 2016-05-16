package main.logger;

import main.core.Handler;

public interface Log extends Handler{
	/*
	 * log the message
	 */
	public void log(String msg);
	
	/*
	 * log the Throwable
	 */
	public void log(Throwable t,String msg);
}
