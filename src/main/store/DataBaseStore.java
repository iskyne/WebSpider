package main.store;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import main.core.AbstractHandler;
import main.dao.Article;
import main.dao.DataSourceFactory;
import main.dao.Mapper;
import main.dao.SqlSessionFactoryUtil;
import main.resourceFactory.ResourceFactory;
import main.context.StandardContext;

public class DataBaseStore extends AbstractHandler{
	
	ExecutorService threadPool=null;
	
	ResourceFactory<StringBuffer> textQueue=null;
	
	private volatile boolean stop=false;
	
	private DataSource dataSource;
	
	private SqlSessionFactory sqlSessionFactory;
	
	public DataBaseStore(){
		
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		threadPool=Executors.newFixedThreadPool(50);
		textQueue=context.getTextQueue();
		dataSource=(DataSource)((StandardContext) getContext()).getApplicationContext().getBean("dataSource");
				
		TransactionFactory factory=new JdbcTransactionFactory();
		Environment environment=new Environment("dev",factory,dataSource);
		Configuration configuration=new Configuration(environment);
		configuration.addMapper(Mapper.class);
		SqlSession session=null;
		sqlSessionFactory=new SqlSessionFactoryBuilder().build(configuration);
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
					System.out.println(textQueue.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch(Exception e){
					e.printStackTrace();
				} catch(Throwable e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void store(StringBuffer sb){
		String[] articleSep=sb.toString().split(System.getProperty("line.separator"));
		Article article=new Article(articleSep[0],articleSep[1]);

		SqlSession session=null;
		try{
			session=sqlSessionFactory.openSession();
			session.insert("main.dao.Mapper.insertArticle", article);
			session.commit();
		}finally{
			session.close();
		}
	}

}
