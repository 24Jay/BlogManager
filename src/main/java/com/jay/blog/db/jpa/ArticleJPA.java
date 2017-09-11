package com.jay.blog.db.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.jay.blog.db.entity.Article;

public interface ArticleJPA extends CrudRepository<Article, Long>
{
	@Query("select id, title, content from Article")
	public Iterable<Article> findAlls();

	
//	public Article findById(Long id);
//	@Modifying(clearAutomatically = true)
//	@Query("update article  a set a.title=:title, a.content=:content where id=:id")
//	public Article updateById(@Param("title")String title, @Param("content")String content, @Param("id")Long id);
}
