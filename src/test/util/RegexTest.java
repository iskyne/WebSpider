package test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.util.Constant;

public class RegexTest {
	public static void find(String filePath,String regex){
		File file=null;
		FileInputStream inputStream=null;
		FileChannel channel=null;
		Charset charset=Charset.forName(Constant.DEFAULT_ENCODING);
		String outputText=null;
		StringBuffer outputBuffer=null;
		//read the web page
		try{
			file=new File(filePath);
			inputStream=new FileInputStream(file);
			channel=inputStream.getChannel();
			
			ByteBuffer buffer=ByteBuffer.allocate(1024);
			outputBuffer=new StringBuffer(1024);
			int count=0;
			while(channel.position()<channel.size()){
				channel.read(buffer);
				buffer.flip();
				outputBuffer.append(charset.decode(buffer));
				buffer.clear();
			}
			outputText=outputBuffer.toString();		
			//the logic below will output the content
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		//get the title
		String title=null;
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(outputText);
		while(m.find()){
			title=m.group();
			System.out.println(title);
		}
		
		//get the content under title
		int index=outputBuffer.indexOf(title)+title.length();
		StringBuffer articleContent=getArticleContent(outputBuffer,index);
		System.out.println(articleContent.toString());
		
		
	}
	
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
	
	public static void main(String args[]){
		find("E:\\IskyneWorkSpace\\MyEclipse\\WebSpider\\content\\file2",
				Constant.REGEX_MAPPING_TITLE);
	}
}
