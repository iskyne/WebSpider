package main.util;

public class Constant {
	//the default encoding in web page
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	
	//the regex expression to match title in web page
	public static final String REGEX_MAPPING_TITLE =
			"(?<=<div class=\"otitle\">)(\n\r)*.*([\u4E00-\u9FFF]+.*?[\u4E00-\u9FFF]*.*?)(?=</div>)";
	
	//the url blocking queue size
	public static final int DEFAULT_URL_BLOCKINGQUEUE_SIZE = 10000;
	
	//the text data queue size
	public static final int DEFAULT_TEXTDATA_BLOCKINGQUEUE_SIZE = 10000;
	
	//the default number of spiders in container
	public static final int DEFAULT_SPIDERS_NUMBERS = 100;
	
	//the default number of spider thread pool size
	public static final int DEFAULT_THREADPOOL_SIZE = 100;
	
	//the default number of segment processor 
	public static final int DEFAULT_SEGMENT_PROCESSOR_NUMBERS = 50;
	
	//the default number of segment thread pool size
	public static final int DEFAULT_SEGMENT_THREADPOOL_SIZE = 50;
	
	//the default size to initialize blocking list
	public static final int DEFAULT_BLOCKING_LIST_SIZE = 10000;
}
