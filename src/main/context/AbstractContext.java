package main.context;

import java.net.URL;
import java.util.concurrent.BlockingQueue;

import main.core.Container;
import main.logger.Log;
import main.parser.Parser;

public abstract class AbstractContext implements Context{

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChild(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container getChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChild(int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearChild() {
		// TODO Auto-generated method stub
		
	}
}
