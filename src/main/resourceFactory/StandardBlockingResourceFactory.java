package main.resourceFactory;

import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import main.core.AbstractHandler;
import main.store.Resource;
import main.util.Constant;

public class StandardBlockingResourceFactory<T> extends AbstractHandler implements ResourceFactory<T>{

	private BlockingQueue<T> blockingQueue=null;
	
	private int maxSize=Constant.DEFAULT_BLOCKING_LIST_SIZE;
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		blockingQueue=new ArrayBlockingQueue<T>(maxSize,false);
	}

	@Override
	public T get() throws InterruptedException {
		// TODO Auto-generated method stub
		return blockingQueue.take();
	}
	@Override
	public void put(T t) throws InterruptedException {
		// TODO Auto-generated method stub
		blockingQueue.put(t);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return blockingQueue.size();
	}
	
	public int getMaxSize(){
		return this.maxSize;
	}
	
	public void setMaxSize(int maxSize){
		this.maxSize=maxSize;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
