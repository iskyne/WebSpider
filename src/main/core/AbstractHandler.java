package main.core;

import main.context.Context;

public abstract class AbstractHandler extends AbstractContainer implements Handler{
	protected Context context=null;

	@Override
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context=context;
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return this.context;
	}
	
}
