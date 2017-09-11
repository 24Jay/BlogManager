package com.jay.blog.db.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import com.jay.blog.db.entity.Article;
import com.jay.blog.db.entity.ArticleObject;
import com.jay.blog.db.entity.User;

public class ArticleConverter
{
	/****
	 * 把数据库使用的Entity转换为用户直接使用的对象，去除其中隐含的多余信息<br>
	 * 
	 * @param a
	 * @return
	 */
	public static ArticleObject convert(Article a)
	{
		if (a == null)
			return null;
		return new ArticleObject(a.getId(), a.getTitle(), a.getContent(), a.getAuthor().getUserId());
	}

	/***
	 * 把AritcleObject转换为Article对象，便于数据库操作<br>
	 * 
	 * @param o
	 * @return
	 */
	public static Article reconvert(ArticleObject o)
	{
		/***
		 * 使用当前用户添加
		 */

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("currentUser");
		Article article = new Article();
		article.setAuthor(user);

		if (o == null)
		{
			article.setContent("");
			article.setTitle("");
			article.setId(new Long(0));
			return article;
		}
		article.setContent(o.getContent());
		article.setId(o.getId());
		article.setTitle(o.getTitle());
		return article;

	}

}
