package main.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buffer.WebPageBuffer;
import main.container.Container;
import main.core.Lifecycle;
import main.parser.Parser;

public class Spider implements Runnable,Lifecycle{
	
	private Container container=null;
	
	private BlockingQueue<URL> urlsQueue=null;
	
	private BlockingQueue<StringBuffer> textQueue=null;
	
	/*
	 * indicates the working status
	 */
	private volatile boolean stop=true;
	
	/*
	 * the core process,for getting the text content in the web page the url point to.
	 */
	public WebPageBuffer crawlContent(URL url) throws IOException{
		WebPageBuffer result=new WebPageBuffer();
		//get the data in the http response
		InputStream data=url.openStream();
		byte[] buffer=new byte[1024];
		int len=0;
		StringBuffer contentBuffer=new StringBuffer(4086);
		while((len=data.read(buffer))!=-1){
			contentBuffer.append(new String(buffer,0,len));
		}
		
		return Parser.parse(contentBuffer);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop){
			try {
				URL url=urlsQueue.take();
				WebPageBuffer parseResult=crawlContent(url);
				urlsQueue.addAll(parseResult.getUrls());
				this.textQueue.add(parseResult.getArticle());
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
		if(container==null){
			setContainer(Container.getInstance());
		}
		
		this.urlsQueue=container.getURLQueue();
		this.textQueue=container.getTextQueue();
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
	public void setContainer(Container container){
		this.container=container;
	}
	
	/*
	 * return the container
	 */
	public Container getContainer(){
		return this.container;
	}
	
	/*
	 * clear the spider object for reusing.
	 */
	public void clear(){
		setContainer(null);
		this.textQueue=null;
		this.urlsQueue=null;
	}
	
	public static void main(String args[]){
		Spider spider=new Spider();
		try {
			System.out.println(spider.crawlContent(
					new URL("file:\\Users\\Administrator\\Desktop\\http _opinion.people.com.cn_n_2014_0113_c1003-24095314.html")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
