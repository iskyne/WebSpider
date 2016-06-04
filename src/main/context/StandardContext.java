package main.context;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
import main.spider.StandardBloomFilter;
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
	 * the blocking queue for store the text which not be separated
	 */
	private ResourceFactory<StringBuffer> TextFactory=new StandardBlockingResourceFactory<StringBuffer>();
	
	/*
	 * spider factory focus on crawl web page
	 */
	private Handler spiderFactory ;
	
	/*
	 * bloom filter for skipping the repeat url
	 */
	private StandardBloomFilter urlFilter;
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
	public Parser parser;
	
	/*
	 * logger
	 */
	public Log log;
	
	/*
	 * Bean factory 
	 */
	private ApplicationContext applicationContext;
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
		setApplicationContext(new ClassPathXmlApplicationContext("ApplicationContext.xml"));
		
		this.urlFilter=(StandardBloomFilter) applicationContext.getBean("urlsFilter");
		this.parser=(Parser) applicationContext.getBean("parser");
		this.log=(Log) applicationContext.getBean("log");
		this.dataBaseStore= (Handler) applicationContext.getBean("databaseStore");
		this.spiderFactory=(Handler) applicationContext.getBean("spiderFactory");
		
		((StandardBlockingResourceFactory<URL>) urlFactory).setMaxSize(10000000);
		((Handler) this.urlFactory).initialize();
		((StandardBlockingResourceFactory<URL>) urlFactory).setMaxSize(100000);
		((Handler) this.TextFactory).initialize();
		
		dataBaseStore.setContext(this);
		dataBaseStore.initialize();
		
		((Handler) urlFactory).setContext(this);
		((Handler) TextFactory).setContext(this);
		
		parser.setContext(this);
		log.setContext(this);
		
		spiderFactory.setContext(this);
		((ReusableHandler)spiderFactory).initialize();
	}
	
	/*
	 * start the container
	 */
	@Override
	public void start(){
		((ReusableHandler)spiderFactory).start();
		
		dataBaseStore.start();
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
	
	public void setParser(Parser parser){
		this.parser=parser;
	}
	

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
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
	
	public StandardBloomFilter getUrlFilter() {
		return urlFilter;
	}

	public void setUrlFilter(StandardBloomFilter urlFilter) {
		this.urlFilter = urlFilter;
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
	
	public static void main(String args[]){
		StandardContext context=StandardContext.getInstance();
		try {
			context.initialize();
			context.getURLQueue().put((new URL("http://localhost:8080/WebServer")));
			context.start();
			//Thread.sleep(10000);
			//context.getLog().log("---------"+context.getTextQueue().size()+" "+context.getURLQueue().size()+"-------------- ");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(InterruptedException e){
			e.printStackTrace();
		}
	}
}
