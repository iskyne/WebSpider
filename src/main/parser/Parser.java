package main.parser;

import java.net.MalformedURLException;

import main.core.Handler;
import main.store.TransferBuffer;

public interface Parser extends Handler{
	
	public TransferBuffer parse(StringBuffer page) throws MalformedURLException;
	
}
