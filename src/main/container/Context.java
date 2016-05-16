package main.container;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

import main.core.Container;
import main.parser.Parser;

public interface Context extends Container{
	/*
	 * get the parser
	 */
	Parser getParser();
	
	/*
	 * get the urls queue
	 */
	BlockingQueue<URL> getURLQueue();
	
	/*
	 * get the text queue
	 */
	BlockingQueue<StringBuffer> getTextQueue();

}
