package main.dao;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class SqlSessionFactoryUtil {
	public static SqlSessionFactory getSqlSessionFactory(){
		DataSource ds=DataSourceFactory.getBlogDataSource();
		TransactionFactory factory=new JdbcTransactionFactory();
		Environment environment=new Environment("test",factory,ds);
		Configuration configuration=new Configuration(environment);
		configuration.addMapper(Mapper.class);
		SqlSession session=null;
		SqlSessionFactory sessionFactory=new SqlSessionFactoryBuilder().build(configuration);
		return sessionFactory;
	}
}
