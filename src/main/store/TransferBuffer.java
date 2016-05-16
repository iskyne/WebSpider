package main.store;

import java.net.URL;
import java.util.List;

import main.core.Reuse;

public interface TransferBuffer extends Reuse{
	/*
	 * add url to the buffer
	 */
	public void addUrl(URL rul);
	
	/*
	 * add the resource to the buffer
	 */
	public void addResource(Resource resource);
	
	/*
	 * get urls
	 */
	
	public List<URL> getUrls();
	
	/*
	 * get resource list
	 */
	public List<Resource> getResources();
}
