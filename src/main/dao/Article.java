package main.dao;

public class Article {
	
	private int articleId;
	private String articleTitle;
	private String articleContent;
	
	public Article(String title,String content){
		this.articleTitle=title;
		this.articleContent=content;
	}
	
	public Article(int id,String content){
		this.articleId=id;
		this.articleContent=content;
	}
	
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
}
