package main.dao;

import org.apache.ibatis.annotations.Insert;

public interface Mapper {
	@Insert("insert into Article(article_title,article_content) values(#{articleTitle},#{articleContent})")
	public void insertArticle(Article article);
}
