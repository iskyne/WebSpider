package main.core;

public interface Container extends Lifecycle{
	/*
	 * return the parent container
	 */
	public Container getParent();
}
