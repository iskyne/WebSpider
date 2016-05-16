package main.store;

public interface Resource<T> {
	/*
	 * get resource
	 */
	public T getResource();
	
	/*
	 * set resource
	 */
	public void setResource(T resource);
}
