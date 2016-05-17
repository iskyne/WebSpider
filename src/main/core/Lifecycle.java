package main.core;

public interface Lifecycle {
	/*
	 * initialize 
	 */
	public void initialize();
	
	/*
	 * start the component
	 */
	public void start();
	
	/*
	 * stop the component
	 */
	public void stop();
}
