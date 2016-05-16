package main.container;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import main.core.Container;
import main.core.Lifecycle;
import main.logger.FileLog;
import main.parser.Parser;
import main.parser.StandardParser;
import main.spider.StandardSpider;
import main.util.Constant;
import main.wordprocess.Segmentor;

public class StandardContext implements Context{
	/*
	 * the blocking queue for store the urls which not be processed
	 */
	private BlockingQueue<URL> URLQueue=new ArrayBlockingQueue<URL>(Constant.DEFAULT_URL_BLOCKINGQUEUE_SIZE,false);
	
	/*
	 * the blocking queue for store the text which not be seperatored
	 */
	private BlockingQueue<StringBuffer> TextDataQueue=new ArrayBlockingQueue<StringBuffer>(Constant.DEFAULT_TEXTDATA_BLOCKINGQUEUE_SIZE,false);
	
	/*
	 * the thread pool processing spider task
	 */
	private Executor spiderWorkers=Executors.newFixedThreadPool(Constant.DEFAULT_THREADPOOL_SIZE);
	
	/*
	 * the data structure storing spiders 
	 */
	private Stack<StandardSpider> spiders=new Stack<StandardSpider>();
	
	/*
	 * the thread pool processing separator words
	 */
	private Executor segmentWorkers=Executors.newFixedThreadPool(Constant.DEFAULT_SEGMENT_THREADPOOL_SIZE);
	
	/*
	 * the data structure storing segment processor
	 */
	private Stack<Segmentor> Segmentors=new Stack<Segmentor>();
	
	/*
	 *  charset
	 */
	public static final Charset utf8Charset=Charset.forName("utf-8");
	
	/*
	 * the web page parser
	 */
	public final Parser parser=StandardParser.getInstance();
	/*
	 * logger
	 */
	public FileLog log=new FileLog();
	/*
	 * singleton design pattern
	 */	
	private static class ContainerHolder{
		private static final StandardContext instance=new StandardContext();
	}
	
	public static StandardContext getInstance(){
		return ContainerHolder.instance;
	}
	
	/*
	 * start the container
	 */
	@Override
	public void start(){
		StandardContext container=StandardContext.getInstance();
		for(int i=0;i<Constant.DEFAULT_SPIDERS_NUMBERS;i++){
			StandardSpider spider=new StandardSpider();
			spider.start();
			spiders.push(spider);
			container.spiderWorkers.execute(spider);
		}
		
		for(int i=0;i<Constant.DEFAULT_SEGMENT_PROCESSOR_NUMBERS;i++){
			Segmentor segmentor=new Segmentor();
			segmentor.start();
			Segmentors.push(segmentor);
			container.segmentWorkers.execute(segmentor);
		}
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * return the URLs queue
	 */
	public BlockingQueue<URL> getURLQueue(){
		return this.URLQueue;
	}
	
	/*
	 * return the text queue
	 */
	public BlockingQueue<StringBuffer> getTextQueue(){
		return this.TextDataQueue;
	}
	
	/*
	 * (non-Javadoc)
	 * get the parser
	 */
	public Parser getParser(){
		return this.parser;
	}
	
	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String args[]){
		StandardContext context=StandardContext.getInstance();
		try {
			context.getURLQueue().add(new URL("http://localhost:8080/WebServer/"));
			context.start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
