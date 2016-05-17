package main.parser;

import java.net.MalformedURLException;

import main.context.Context;
import main.core.AbstractHandler;
import main.core.Container;
import main.store.TransferBuffer;

public abstract class AbstractParser extends AbstractHandler implements Parser{

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

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TransferBuffer parse(StringBuffer page) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
