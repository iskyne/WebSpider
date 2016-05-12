package test.util;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueTest {
	public static void main(String args[]){
		ArrayBlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(2,false);
		
		try {
			queue.put(2);
			queue.put(1);
			System.out.println(queue.take());
			System.out.println(queue.take());
			System.out.println(queue.take());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
