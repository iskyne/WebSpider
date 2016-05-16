package main.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.context.Context;
import main.context.StandardContext;
import main.core.Lifecycle;
import main.parser.Parser;
import main.parser.StandardParser;
import main.store.Resource;
import main.store.TransferBuffer;
import main.store.WebPageBuffer;

public class StandardSpider implements Spider,Runnable{
	
	private Context context=null;
	
	private BlockingQueue<URL> urlsQueue=null;
	
	private BlockingQueue<StringBuffer> textQueue=null;
	
	private Parser parser=null;
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
				URL url=urlsQueue.take();
				TransferBuffer parseResult=crawlContent(url);
				for(URL u:parseResult.getUrls()){
					urlsQueue.put(u);
				}

				for(Resource<StringBuffer> resource:parseResult.getResources()){
					textQueue.put(resource.getResource());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		if(context==null){
			setContext(StandardContext.getInstance());
		}
		
		this.urlsQueue=context.getURLQueue();
		this.textQueue=context.getTextQueue();
		this.parser=context.getParser();
		this.stop=false;
	}

	@Override
	public synchronized void stop() {
		// TODO Auto-generated method stub
		clear();
		this.stop=true;
	}
	
	/*
	 * set the container
	 */
	public void setContext(Context context){
		this.context=context;
	}
	
	/*
	 * return the container
	 */
	public Context getContext(){
		return this.context;
	}
	
	/*
	 * clear the spider object for reusing.
	 */
	public void clear(){
		setContext(null);
		this.textQueue=null;
		this.urlsQueue=null;
	}
	
	public static void main(String args[]){
		StandardSpider spider=new StandardSpider();
		try {
			System.out.println(spider.crawlContent(
					new URL("file:\\Users\\Administrator\\Desktop\\http _opinion.people.com.cn_n_2014_0113_c1003-24095314.html")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}
