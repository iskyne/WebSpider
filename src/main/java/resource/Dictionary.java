package main.java.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public interface Dictionary {
	/*
	 * get Reader
	 */
	public Reader getReader() throws FileNotFoundException;
	
	/*
	 * get Writer
	 */
	public PrintWriter getWriter() throws IOException;
	
	/*
	 * set Reader
	 */
	public void setReader(Reader reader);
	
	/*
	 * set writer
	 */
	public void setWriter(PrintWriter writer);
	
	/*
	 *  add Dictionary word
	 */
	public void addWord(String word);
}
