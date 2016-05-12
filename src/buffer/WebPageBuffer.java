package buffer;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class WebPageBuffer {
	/*
	 * storing the urls in one page
	 */
	private List<URL> urls;
	
	/*
	 * storing the article
	 */
	private StringBuffer article;
	
	public WebPageBuffer(){
		if(urls==null){
			setUrls(new LinkedList<URL>());
		}
	}

	public List<URL> getUrls() {
		return urls;
	}

	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}
	
	public void addRul(URL url){
		this.urls.add(url);
	}
	
	public void clearUrl(){
		this.urls.clear();
	}

	public StringBuffer getArticle() {
		return article;
	}

	public void setArticle(StringBuffer article) {
		this.article = article;
	}
	
	public void clear(){
		clearUrl();
		setArticle(null);
	}
}
