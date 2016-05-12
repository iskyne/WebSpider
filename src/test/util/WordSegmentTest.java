package test.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jeasy.analysis.MMAnalyzer;

public class WordSegmentTest {
	public static void main(String args[]){
		MMAnalyzer analyzer=new MMAnalyzer();
		String text="习近平同志在网络安全和信息化工作座谈会上指出，网络空间是亿万民众共同的精神家园。"
				+ "建设好这个精神家园，互联网企业是重要主体，既要讲发展，也要讲责任。互联网企业生存在社会之中，"
				+ "不能只讲经济责任、法律责任，还要讲社会责任、道德责任。我国互联网企业在快速发展过程中，"
				+ "承担了很多社会责任，作出了大量社会贡献，但也有一些企业只顾发展、不讲责任，或是在发展中忽视了责任这一面，"
				+ "导致不少问题。近来发生的一些互联网事件，凸显了增强互联网企业社会责任意识的紧迫性";
		
		LinkedHashMap<String,Integer> statisticTab=new LinkedHashMap<String,Integer>();
		
		try{
			/*
			 * split the result into string array.
			 */
			String[] words=analyzer.segment(text, "|").split("|");
			for(String str:words){
				if(!statisticTab.containsKey(str)){
					statisticTab.put(str, 1);
				}else{
					statisticTab.put(str, statisticTab.get(str)+1);
				}
			}
			
			Iterator<Entry<String,Integer>> it=statisticTab.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Integer> entry=it.next();
				System.out.println(entry.getKey()+" "+entry.getValue());
			}
			
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
