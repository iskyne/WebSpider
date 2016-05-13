package main.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.buffer.WebPageBuffer;
import main.util.Constant;

public class Parser {
	/*
	 * get the urls and article in the page.
	 */
	public static WebPageBuffer parse(StringBuffer page) throws MalformedURLException{
		//System.out.println(page.toString());
		WebPageBuffer result=new WebPageBuffer();
		//TODO get utls in the page
		result.addRul(new URL("http://politics.people.com.cn/n1/2016/0512/c1001-28345037.html"));
		
		//get the article title
		Pattern p=Pattern.compile(Constant.REGEX_MAPPING_TITLE);
		Matcher m=p.matcher(page);
		String title="学习贯彻习近平网信座谈会讲话 各省“一把手”表态";
		while(m.find()){
			title=m.group();
			System.out.println(title);
		}
		
		//get the content under title
		int index=page.indexOf(title)+title.length();
		//StringBuffer articleContent=getArticleContent(page,index).append(title);
		StringBuffer articleContent=new StringBuffer("为人民服务，习近平");
		result.setArticle(articleContent);
		System.out.println(articleContent);
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
