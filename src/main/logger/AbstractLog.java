package main.logger;

import main.context.Context;
import main.core.AbstractHandler;
import main.core.Container;

public abstract class AbstractLog extends AbstractHandler implements Log{
	protected int logLevel;
	
	public static final int ERROR=3;
	public static final int WARNING=2;
	public static final int DEBUG=1;
	public static final int INFO=0;
	
	protected String[] logLevelMsg={"INFO : ","DEBUG : ","WARNING : ","ERROR : "};
	
	public int getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(int logLevel) {
		this.logLevel = logLevel;
	}
	
	public abstract void log(String msg,int level);
	
}
