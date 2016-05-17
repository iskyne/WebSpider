package main.core;

public interface Container extends Lifecycle{
	/*
	 * return the parent container
	 */
	public Container getParent();
	
	/*
	 * add child
	 */
	public void addChild(Container container);
	
	/*
	 * return the ith child
	 */
	public Container getChild(int index);
	
	/*
	 * remove child
	 */
	public void removeChild(int index);
	
	/*
	 * clear child
	 */
	public void clearChild();
}
