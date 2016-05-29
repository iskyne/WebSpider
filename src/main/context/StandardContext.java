package main.context;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import main.analysis.Segmentor;
import main.core.AbstractHandler;
import main.core.Container;
import main.core.Handler;
import main.core.Lifecycle;
import main.core.ReusableHandler;
import main.logger.FileLog;
import main.logger.Log;
import main.parser.Parser;
import main.parser.StandardParser;
import main.resourceFactory.ResourceFactory;
import main.resourceFactory.StandardBlockingResourceFactory;
import main.spider.StandardSpider;
import main.spider.StandardSpiderFactory;
import main.store.DataBaseStore;
import main.util.Constant;

public class StandardContext extends AbstractContext{
	/*
	 * the blocking queue for store the urls which not be processed
	 */
	private ResourceFactory<URL> urlFactory=new StandardBlockingResourceFactory<URL>();
	
	/*
	 * the blocking queue for store the text which not be seperatored
	 */
	private ResourceFactory<StringBuffer> TextFactory=new StandardBlockingResourceFactory<StringBuffer>();
	
	/*
	 * spider factory focus on crawl web page
	 */
	private Handler spiderFactory ;
	
	/*
	 * segmentor factory
	 */
	private Handler dataBaseStore;
	
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
	public static final Charset utf8Charset=Charset.forName("GB2312");
	
	/*
	 * the web page parser
	 */
	public final Parser parser=StandardParser.getInstance();
	/*
	 * logger
	 */
	public Log log=FileLog.getInstance();
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
	 * initialize the context;
	 */
	@Override
	public void initialize(){
		((Handler) this.urlFactory).initialize();
		((Handler) this.TextFactory).initialize();
	}
	
	/*
	 * start the container
	 */
	@Override
	public void start(){
		spiderFactory=new StandardSpiderFactory();
		spiderFactory.setContext(this);
		((ReusableHandler)spiderFactory).initialize();
		((ReusableHandler)spiderFactory).start();
		
//		for(int i=0;i<Constant.DEFAULT_SEGMENT_PROCESSOR_NUMBERS;i++){
//			Segmentor segmentor=new Segmentor();
//			segmentor.start();
//			Segmentors.push(segmentor);
//			segmentWorkers.execute(segmentor);
//		}
		
		dataBaseStore= new DataBaseStore();
		dataBaseStore.setContext(this);
		dataBaseStore.initialize();
		dataBaseStore.start();
		
		((Handler) urlFactory).setContext(this);
		((Handler) TextFactory).setContext(this);
		
		parser.setContext(this);
		log.setContext(this);
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * (non-Javadoc)
	 * get the parser
	 */
	public Parser getParser(){
		return this.parser;
	}
	
	@Override
	public ResourceFactory getURLQueue() {
		// TODO Auto-generated method stub
		return (ResourceFactory) this.urlFactory;
	}

	@Override
	public ResourceFactory getTextQueue() {
		// TODO Auto-generated method stub
		return (ResourceFactory) this.TextFactory;
	}
	
	@Override
	public Log getLog(){
		return this.log;
	}
	
	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String args[]) throws InterruptedException{
		StandardContext context=StandardContext.getInstance();
		try {
			context.initialize();
			context.getURLQueue().put((new URL("http://www.people.com.cn/")));
			context.start();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
