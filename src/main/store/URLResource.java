package main.store;

import java.net.URL;

public class URLResource implements Resource{
	
	private URL resource;
	
	@Override
	public Object getResource() {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public void setResource(Object resource) {
		// TODO Auto-generated method stub
		this.resource=(URL) resource;
	}

}
