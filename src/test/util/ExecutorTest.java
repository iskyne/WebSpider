package test.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorTest implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized(this.getClass()){
			System.out.println(Thread.currentThread().toString());
			try {
				Thread.currentThread().sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String args[]){
		Executor executor=Executors.newSingleThreadExecutor();
		executor.execute(new ExecutorTest());
		executor.execute(new ExecutorTest());
	}
}
