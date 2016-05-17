package main.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import main.context.Context;
import main.core.AbstractHandler;
import main.core.Container;
import main.core.Handler;
import main.core.ReusableHandler;
import main.util.Constant;

public class StandardSpiderFactory extends AbstractHandler implements ReusableHandler{
	/*
	 * store the spider workers.
	 */
	private List<Container> spiderFactory;
	
	/*
	 * (non-Javadoc)
	 * thread pool for spider
	 */
	private ExecutorService threadPool;
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		spiderFactory=new ArrayList<Container>(Constant.DEFAULT_SPIDERS_NUMBERS);
		threadPool=Executors.newFixedThreadPool(Constant.DEFAULT_THREADPOOL_SIZE);
		for(int i=0;i<Constant.DEFAULT_SPIDERS_NUMBERS;i++){
			Spider spider=new StandardSpider();
			spider.initialize();
			this.addChild(spider);
		}
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		spiderFactory=null;
		threadPool=null;
		for(int i=0;i<Constant.DEFAULT_SPIDERS_NUMBERS;i++){
			Container spider=getChild(i);
			if(spider instanceof ReusableHandler){
				((ReusableHandler) spider).clear();
			}
		}
		clear();
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Container container) {
		// TODO Auto-generated method stub
		this.spiderFactory.add(container);
	}
	
	@Override
	public Container getChild(int index) {
		// TODO Auto-generated method stub
		return spiderFactory.get(index);
	}

	@Override
	public void removeChild(int index) {
		// TODO Auto-generated method stub
		spiderFactory.remove(index);
	}

	@Override
	public void clearChild() {
		// TODO Auto-generated method stub
		spiderFactory.clear();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		for(int i=0;i<Constant.DEFAULT_SPIDERS_NUMBERS;i++){
			Container child=getChild(i);
			if(child instanceof Spider){
				threadPool.execute((Spider) child);
			}
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		threadPool.shutdown();
	}
}
