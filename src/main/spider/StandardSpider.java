package main.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.context.Context;
import main.context.StandardContext;
import main.core.AbstractHandler;
import main.core.Container;
import main.core.Lifecycle;
import main.logger.Log;
import main.parser.Parser;
import main.parser.StandardParser;
import main.resourceFactory.ResourceFactory;
import main.store.Resource;
import main.store.TransferBuffer;
import main.store.WebPageBuffer;

public class StandardSpider extends AbstractHandler implements Spider{
	
	
	private ResourceFactory<URL> urlsQueue=null;
	
	private ResourceFactory<StringBuffer> textQueue=null;
	
	private Parser parser=null;
	
	private Log log=null;
	
	private StandardBloomFilter urlsFilter=null;
	/*
	 * indicates the working status
	 */
	private volatile boolean stop=true;
	
	/*
	 * the core process,for getting the text content in the web page the url point to.
	 */
	public TransferBuffer crawlContent(URL url) throws IOException{
		WebPageBuffer result=new WebPageBuffer();
		//get the data in the http response
		InputStream data=url.openStream();
		byte[] buffer=new byte[1024];
		int len=0;
		StringBuffer contentBuffer=new StringBuffer(4086);
		while((len=data.read(buffer))!=-1){
			contentBuffer.append(new String(buffer,0,len,StandardContext.utf8Charset));
		}
		
		return parser.parse(contentBuffer);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop){
			try {
				Thread.currentThread().sleep(2000);
				URL url=urlsQueue.get();
				TransferBuffer parseResult=crawlContent(url);
				for(URL u:parseResult.getUrls()){
					if(!urlsFilter.contain(u.toString())){
						urlsFilter.add(u.toString());
						urlsQueue.put(u);
					}
				}
	
				for(Resource<StringBuffer> resource:parseResult.getResources()){
					if(resource.getResource()!=null){
						textQueue.put(resource.getResource());
					}
				}
				log.log("current url size : "+urlsQueue.size());
				log.log("current text size : "+textQueue.size());
				log.log(Thread.currentThread().toString()+" "+Thread.currentThread().isAlive());
			
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		if(context==null){
			setContext(StandardContext.getInstance());
		}
		
		this.urlsQueue=context.getURLQueue();
		this.textQueue=context.getTextQueue();
		this.parser=context.getParser();
		this.log=context.getLog();
		this.urlsFilter=((StandardContext)context).getUrlFilter();
		this.stop=false;
	}
	/*
	 * clear the spider object for reusing.
	 */
	public void clear(){
		setContext(null);
		this.textQueue=null;
		this.urlsQueue=null;
	}
	
	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		this.stop=false;
	}

	@Override
	public synchronized void stop() {
		// TODO Auto-generated method stub
		clear();
		this.stop=true;
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearChild() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Container getChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
