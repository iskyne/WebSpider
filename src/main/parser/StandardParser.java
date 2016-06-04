package main.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.context.Context;
import main.logger.Log;
import main.store.StringResource;
import main.store.TransferBuffer;
import main.store.WebPageBuffer;
import main.util.Constant;

public class StandardParser extends AbstractParser{
	

	public StandardParser(){
		initialize();
	}
	
	public TransferBuffer parse(StringBuffer page) throws MalformedURLException{
		//System.out.println(page.toString().substring(0, 100));
		TransferBuffer result=new WebPageBuffer();
			
		Document doc=Jsoup.parse(page.toString());
		
		//log.log(doc.title());
		
		Elements elements=doc.select("a");

		Iterator<Element> it=elements.iterator();
		while(it.hasNext()){
			Element element = it.next();
			String urlStr=element.attr("href");
			if(urlStr.indexOf("http://")==0&&urlStr.contains("people.com.cn")){
				URL url=new URL(urlStr);
				result.addUrl(url);
			}
		}
		/*
		 * select the text with tag "text_show" or "p_content"
		 * in a web page
		 */
		Element textElement=null;
		
		textElement=doc.select("div[class=text_show]").first();
		if(textElement==null||(!textElement.hasText())){
			textElement=doc.select("div[id=p_content]").first();
			if(textElement==null||(!textElement.hasText())){
				return result;
			}
		}
		
		
		result.addResource(new StringResource(new StringBuffer(doc.title())
			.append(System.getProperty("line.separator"))
			.append(textElement.text())));
		//
		return result;
	} 
	
	/*
	 * get the article
	 */
	public static StringBuffer getArticleContent(StringBuffer buffer,int startIndex){
		StringBuffer articleContent=new StringBuffer(1024);
		int paraStart=startIndex;
		int paraEnd=0;
		while(true){
			paraStart=buffer.indexOf("<p>", paraStart);
			if(paraStart==-1){
				break;
			}
			paraEnd=buffer.indexOf("</p>",paraStart);
			if(paraEnd==-1){
				break;
			}
			String tempBuffer=buffer.substring(paraStart+3, paraEnd);
			if(tempBuffer.indexOf("<span id=\"idForLoginPanel\">")!=-1){
				break;
			}
			articleContent.append(tempBuffer);
			paraStart=paraEnd;
		}
		return articleContent;
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context=context;
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this.context;
	}
	
	public static void main(String args[]){
		Document doc=null;
		try {
			doc = Jsoup.connect("http://fanfu.people.com.cn/n1/2016/0603/c64371-28410084.html")
					.get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements elements=doc.select("div[class=text_show]");
		Element element=elements.first();
		//System.out.println(element);
		if(element!=null&&element.hasText()){
			System.out.println(element.text());
		}
		
	}
}
