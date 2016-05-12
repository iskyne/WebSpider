package main.parser;

import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import buffer.WebPageBuffer;
import main.util.Constant;

public class Parser {
	/*
	 * get the urls and article in the page.
	 */
	public static WebPageBuffer parse(StringBuffer page){
		System.out.println(page.toString());
		WebPageBuffer result=new WebPageBuffer();
		//TODO get utls in the page
		
		
		//get the article title
		Pattern p=Pattern.compile(Constant.REGEX_MAPPING_TITLE);
		Matcher m=p.matcher(page);
		String title=null;
		while(m.find()){
			title=m.group();
			System.out.println(title);
		}
		
		//get the content under title
		int index=page.indexOf(title)+title.length();
		StringBuffer articleContent=getArticleContent(page,index).append(title);
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
