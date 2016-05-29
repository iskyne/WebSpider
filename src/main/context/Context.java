package main.context;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

import main.core.Container;
import main.logger.Log;
import main.parser.Parser;
import main.resourceFactory.ResourceFactory;

public interface Context extends Container{
	/*
	 * get the parser
	 */
	public Parser getParser();
	
	/*
	 * get the urls queue
	 */
	public ResourceFactory getURLQueue();
	
	/*
	 * get the text queue
	 */
	public ResourceFactory getTextQueue();
	
	/*
	 * get the logger
	 */
	public Log getLog();
}
