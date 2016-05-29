package main.store;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import main.core.AbstractHandler;
import main.dao.Article;
import main.dao.SqlSessionFactoryUtil;
import main.resourceFactory.ResourceFactory;

public class DataBaseStore extends AbstractHandler{
	
	ExecutorService threadPool=null;
	
	ResourceFactory<StringBuffer> textQueue=null;
	
	private volatile boolean stop=false;
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		threadPool=Executors.newFixedThreadPool(50);
		textQueue=context.getTextQueue();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		stop=false;
		threadPool.execute(new StoreUnit());
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		stop=true;
	}
	
	private class StoreUnit implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!stop){
				try {
					store(textQueue.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void store(StringBuffer sb){
		String[] articleSep=sb.toString().split(System.getProperty("line.separator"));
		Article article=new Article(articleSep[0],articleSep[1]);
		System.out.println(articleSep[0]+" "+articleSep[1]);
		SqlSessionFactory sessionFactory=SqlSessionFactoryUtil.getSqlSessionFactory();
		SqlSession session=null;
		try{
			session=sessionFactory.openSession();
			session.insert("main.dao.Mapper.insertArticle", article);
			session.commit();
		}finally{
			session.close();
		}
	}

}
