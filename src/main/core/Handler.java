package main.core;

import main.context.Context;

public interface Handler extends Container{
	/*
	 * must set the context container for the handler
	 */
	public void setContext(Context context);
	
	/*
	 * return the context
	 */
	public Context getContext();
	
	
}
