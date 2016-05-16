package main.store;

@SuppressWarnings("rawtypes")
public class StringResource implements Resource{
	private StringBuffer buffer;
	
	public StringResource(){
		buffer=new StringBuffer();
	}
	
	public StringResource(String text){
		buffer=new StringBuffer(text);
	}

	@Override
	public Object getResource() {
		// TODO Auto-generated method stub
		return this.buffer;
	}

	@Override
	public void setResource(Object resource) {
		// TODO Auto-generated method stub
		this.buffer=(StringBuffer) resource;
	}

}
