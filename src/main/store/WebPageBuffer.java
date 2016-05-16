package main.store;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class WebPageBuffer implements TransferBuffer{
	/*
	 * storing the urls in one page
	 */
	private List<URL> urls;
	
	/*
	 * storing the article
	 */
	private List<Resource> resources;
	

	public WebPageBuffer(){
		this.initialize();
	}
	
	/*
	 * get the urls list
	 */
	public List<URL> getUrls() {
		return urls;
	}
	
	/*
	 * set the url list
	 */
	public void setUrls(List<URL> urls) {
		this.urls = urls;
	}
	
	@Override
	public void addUrl(URL url){
		this.urls.add(url);
	}
	
	/*
	 * clear the list for reuse
	 */
	public void clearUrl(){
		this.urls.clear();
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	
	@Override
	public void addResource(Resource resource){
		this.resources.add(resource);
	}
	
	/*
	 * clear the resource list for reuse
	 */
	public void clearResources(){
		this.resources.clear();
	}

	@Override
	public void initialize() {
		if(urls==null){
			urls=new LinkedList<URL>();
		}
		
		if(resources==null){
			resources=new LinkedList<Resource>();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * clear the webpagebuffer for reuse
	 */
	@Override
	public void clear(){
		clearUrl();
		clearResources();
	}
}
