package main.resourceFactory;

import main.store.Resource;

public interface ResourceFactory<T> {
	public T get() throws InterruptedException;
	
	public int size();

	void put(T t) throws InterruptedException;
}
