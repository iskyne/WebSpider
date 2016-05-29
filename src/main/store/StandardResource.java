package main.store;

public class StandardResource<T> implements Resource<T>{
	private T t;
	@Override
	public T getResource() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public void setResource(T resource) {
		// TODO Auto-generated method stub
		this.t=resource;
	}
}
