package main.parser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import main.buffer.WebPageBuffer;
import main.util.Constant;

public class Parser {
	/*
	 * get the urls and article in the page.
	 */
	public static WebPageBuffer parse(StringBuffer page) throws MalformedURLException{
		//System.out.println(page.toString().substring(0, 100));
		WebPageBuffer result=new WebPageBuffer();
		//
		Document doc=Jsoup.parse(page.toString());

		Elements elements=doc.select("a");

		Iterator<Element> it=elements.iterator();
		while(it.hasNext()){
			Element element = it.next();
			String urlStr=element.attr("href");
			if(urlStr.indexOf("http://")==0){
				URL url=new URL(urlStr);
				result.addUrl(url);
			}
		}
		Element element1=doc.select("body").first();

		if(element1!=null&&element1.hasText()){
			result.setArticle(new StringBuffer(element1.text()));
		}
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
}
