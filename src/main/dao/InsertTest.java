package main.dao;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class InsertTest {
	public static void main(String args[]){
		DataSource ds=DataSourceFactory.getBlogDataSource();
		TransactionFactory factory=new JdbcTransactionFactory();
		Environment environment=new Environment("test",factory,ds);
		Configuration configuration=new Configuration(environment);
		configuration.addMapper(Mapper.class);
		SqlSession session=null;
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(configuration);
		try{
			session=sessionFactory.openSession();
			Article article=new Article("科学发展观", "文章");
			session.insert("main.dao.Mapper.insertArticle",article);
			session.commit();
		}finally{
			session.close();
		}
	}
}
