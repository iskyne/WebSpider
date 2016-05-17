package test.util;

public class ClassTest {
	public static void main(String args[]){
		StackTraceElement[] elements=Thread.currentThread().getStackTrace();
		StackTraceElement element=elements[elements.length-1];
		System.out.println(element.getMethodName());
	}
}
