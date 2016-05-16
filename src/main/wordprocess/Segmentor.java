package main.wordprocess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;

import main.context.Context;
import main.context.StandardContext;
import main.core.Lifecycle;
import main.java.resource.Dictionary;
import main.java.resource.StandardDictionary;
import jeasy.analysis.MMAnalyzer;

public class Segmentor implements Runnable,Lifecycle{
	/*
	 * container
	 */
	private Context context=null;
	
	/*
	 * the text queue
	 */
	private BlockingQueue<StringBuffer> textQueue=null;
	
	/*
	 * the word segment
	 */
	
	private MMAnalyzer analyzer;
	
	/*
	 * the dictionary added if nessesary
	 */
	private Dictionary dictionary;
	
	/*
	 * indicates the running state
	 */
	private boolean stop=false;

	@SuppressWarnings("unchecked")
	public WordFrequencyEntry[] segment(File filePath,int k) throws IOException{
		//prepare the text string through the file under the path
		if(!filePath.exists()){
			throw new FileNotFoundException(filePath.getCanonicalPath());
		}
		FileChannel channel=null;
		
		Charset charset=Charset.forName("GBK");
		ByteBuffer buffer=ByteBuffer.allocate(2048);
		StringBuffer content=new StringBuffer(2048);
		
		channel=new FileInputStream(filePath).getChannel();

		while(channel.position()<channel.size()){
			channel.read(buffer);
			buffer.flip();
			content.append(charset.decode(buffer));
			buffer.clear();
		}
		String text=content.toString();
		return segment(content,k);
	}
	/*
	 * the core logic for seperator the text into words
	 */
	@SuppressWarnings("unchecked")
	public WordFrequencyEntry[] segment(StringBuffer content,int k) throws IOException{
		//get the analyzer
		//this.container.log.log(content.toString());
		if(analyzer==null){
			analyzer=new MMAnalyzer(10);
			if(dictionary==null){
				dictionary=StandardDictionary.getInstance();
				//analyzer.addDictionary(dictionary.getReader());
			}
		}
		
		Map<String,Integer> statisticTab=new LinkedHashMap<String,Integer>();
		String[] words=null;
		try{
			/*
			 * split the result into string array.
			 */
			String segmentRslt=analyzer.segment(content.toString(), ",");
			words=segmentRslt.split(",");
			
		}catch(IOException e){
			e.printStackTrace();
		}
		for(String str:words){
			if(!statisticTab.containsKey(str)){
				statisticTab.put(str, 1);
			}else{
				statisticTab.put(str, statisticTab.get(str)+1);
			}
		}
			
		Iterator<Entry<String,Integer>> it=statisticTab.entrySet().iterator();
		Queue<WordFrequencyEntry> sortFrequencyWord=new PriorityQueue<WordFrequencyEntry>(
				new Comparator(){

					@SuppressWarnings("rawtypes")
					@Override
					public int compare(Object o1, Object o2) {
						// TODO Auto-generated method stub
						int k1=(Integer) ((WordFrequencyEntry)o1).getK();
						int k2=(Integer) ((WordFrequencyEntry)o2).getK();
						if(k1>k2){
							return -1;
						}else if(k1==k2){
							return 0;
						}else{
							return 1;
						}
					}
					
				});
		int count=0;
		while(it.hasNext()){
			Entry<String,Integer> entry=it.next();
			sortFrequencyWord.add(new WordFrequencyEntry(entry.getValue(),entry.getKey()));
		}
		
//		while(sortFrequencyWord.size()>0){
//			WordFrequencyEntry<Integer,String> entry=sortFrequencyWord.remove();
//			//System.out.println(entry.getK()+" "+entry.getV());
//		}
		//System.out.println(sortFrequencyWord.size());
		WordFrequencyEntry[] rsltTmp=new WordFrequencyEntry[statisticTab.size()];
		
		while(sortFrequencyWord.peek()!=null&&(Integer)((WordFrequencyEntry)sortFrequencyWord.peek()).getK()>=k){
			rsltTmp[count++]=sortFrequencyWord.remove();
		}
		
		WordFrequencyEntry[] rslt=new WordFrequencyEntry[count];
		System.arraycopy(rsltTmp, 0, rslt, 0, count);
		
		return rslt;
	}
	
	public static class WordFrequencyEntry<K,V>{
		private K k;
		private V v;
		WordFrequencyEntry(K k,V v){
			this.setK(k);
			this.setV(v);
		}
		
		/*
		 * setter and getter
		 */
		public K getK() {
			return k;
		}
		public void setK(K k) {
			this.k = k;
		}
		public V getV() {
			return v;
		}
		public void setV(V v) {
			this.v = v;
		}
		
	}
	
	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		if(context==null){
			context=StandardContext.getInstance();
		}
		this.textQueue=context.getTextQueue();
		this.stop=false;
	}
	
	@Override
	public synchronized void stop() {
		// TODO Auto-generated method stub
		this.stop=true;
	}
	
	/*
	 * clear the object state
	 */
	public void clear(){
		this.context=null;
		this.textQueue=null;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!stop){
			StringBuffer text = null;
			try {
				text = textQueue.take();
				//context.getLog().log(text.toString());
				System.out.println(text);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			WordFrequencyEntry[] entrys = null;
			try {
				entrys = segment(text, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(WordFrequencyEntry<Integer,String> entry:entrys){
				//System.out.println(entry.getK()+" "+entry.getV());
			}
		}
	}
	
	/*
	 * setter and getter
	 */
	public void setMMAnalyzer(MMAnalyzer analyzer){
		this.analyzer=analyzer;
	}
	
	public MMAnalyzer getAnalyzer(){
		return this.analyzer;
	}
	
	public static void main(String args[]){
		Segmentor seg=new Segmentor();
		
		Segmentor.WordFrequencyEntry[] entrys = null;
		String file1="file1";
		try {
			entrys = seg.segment(new File(file1),3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Segmentor.WordFrequencyEntry entry: entrys){
			System.out.println(entry.getK()+" "+entry.getV());
		}
	}
}
