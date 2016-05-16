package main.java.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;

public class StandardDictionary implements Dictionary{
	private Reader reader=null;
	private PrintWriter writer=null;
	public static final String DICTIONARY_PATH="./dictionary/dictionary";
	/*
	 * singleton instance
	 */
	public static class DictionaryInstance{
		static final Dictionary instance = new StandardDictionary();
	}
	/*
	 * singleton method
	 */
	public static Dictionary getInstance() throws IOException{
		Dictionary resource= DictionaryInstance.instance;
		if(resource.getReader()==null){
			//System.out.println(new File(DICTIONARY_PATH).getCanonicalPath());
			resource.setReader(new InputStreamReader(new FileInputStream(DICTIONARY_PATH)));
		}
		if(resource.getWriter()==null){
			resource.setWriter(new PrintWriter(new FileWriter(new File(DICTIONARY_PATH),true)));
		}
		return resource;
	}
	
	/*
	 * return the reader object of the dictionary file
	 */
	@Override
	public Reader getReader() throws FileNotFoundException{
		if(this.reader==null){
			this.reader=new InputStreamReader(new FileInputStream(DICTIONARY_PATH));
		}
		
		return this.reader;
	}
	
	/*
	 * return the writer object of the dictionary file
	 */
	@Override
	public PrintWriter getWriter() throws IOException{
		if(this.writer==null){
			this.writer=new PrintWriter(new OutputStreamWriter(new FileOutputStream(DICTIONARY_PATH)),true);
		}
		return this.writer;
	}
	
	@Override
	public void setReader(Reader reader) {
		this.reader=reader;
	}

	@Override
	public void setWriter(PrintWriter writer) {
		this.writer=writer;
	}
	
	/*
	 * add one word into the dictionary
	 */
	@Override
	public void addWord(String word){
		this.writer.println(word);
		writer.flush();
		if(this.writer!=null){
			writer.close();
		}
	}
	
	public static void main(String args[]){
		try {
			Dictionary dictionary=StandardDictionary.getInstance();
			dictionary.addWord("ÍøÂç°²È«");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
