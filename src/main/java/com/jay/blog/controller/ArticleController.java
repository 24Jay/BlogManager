package com.jay.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jay.blog.Response;
import com.jay.blog.db.entity.Article;
import com.jay.blog.db.entity.ArticleObject;
import com.jay.blog.db.entity.Role;
import com.jay.blog.db.entity.User;
import com.jay.blog.db.jpa.ArticleJPA;
import com.jay.blog.db.util.ArticleConverter;

@RequestMapping("/article")
@RestController
public class ArticleController
{
	@Autowired
	private ArticleJPA articleJPA;

	/***
	 * 添加文章
	 * 
	 * @param article
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	public @ResponseBody Response<ArticleObject> add(@RequestBody ArticleObject article)
	{
		System.out.println("ArticleController--------------->>>add an article");
		/***
		 * 将用户输入的转换为数据库操作需要的，添加user信息
		 */

		Article art = ArticleConverter.reconvert(article);
		Article savedArticle = articleJPA.save(art);
		if (savedArticle == null)
			return new Response(501, Response._501);
		return new Response<ArticleObject>(200, Response._200, ArticleConverter.convert(savedArticle));
	}

	/***
	 * 查询所有文章
	 * 
	 * @return
	 */
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Response<List<ArticleObject>> get()
	{

		List<ArticleObject> list = new ArrayList<ArticleObject>();
		for (Article a : articleJPA.findAll())
		{
			list.add(ArticleConverter.convert(a));
		}

		return new Response<List<ArticleObject>>(200, Response._200, list);
	}

	/***
	 * 根据id来查询文章
	 * 
	 * @param id
	 * @return
	 */
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Response<ArticleObject> getById(@PathVariable Long id)
	{
		ArticleObject articleObject = ArticleConverter.convert(articleJPA.findOne(id));
		if (articleObject == null)
			return new Response(501, Response._501);
		return new Response<ArticleObject>(200, Response._200, articleObject);
	}

	/***
	 * 修改文章
	 * 
	 * @param article
	 * @return
	 */
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	public @ResponseBody Response put(@RequestBody ArticleObject article)
	{
		/***
		 *验证文章是否属于当前用户，如果不属于当前用户，异常退出
		 */
		if (!isArticleOwner(article.getId()))
			return new Response(401, Response._401);

		Article art = ArticleConverter.reconvert(article);
		Article saved = articleJPA.save(art);
		
		if(saved == null )
			return new Response(501, Response._501);
		
		ArticleObject articleObject = ArticleConverter.convert(saved);
		return new Response<ArticleObject>(200, Response._200, articleObject);

	}

	/***
	 * 根据id删除文章
	 * 
	 * @param id
	 * @return
	 */
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	public @ResponseBody Response delete(@PathVariable Long id)
	{
		/**
		 * 首先校验文章是否存在
		 */
		if (!articleJPA.exists(id))
			return new Response(404, Response._404);

		/****
		 * 验证文章属于当前用户，或者用户是admin角色，否则抛出异常
		 */
		if (!(isAdmin() || isArticleOwner(id)))
			return new Response(401, Response._401);

		articleJPA.delete(id);

		/****
		 * 检查是否删除成功
		 */
		if (!articleJPA.exists(id))
			return new Response(200, Response._200);
		else
			return new Response(501, Response._501);

	}

	
	/***
	 *　检查文章是否属于当前用户
	 * @param articleId
	 * @return
	 */
	private boolean isArticleOwner(Long articleId)
	{
		Subject subject = SecurityUtils.getSubject();
		Article article = articleJPA.findOne(articleId);
		Session session = subject.getSession();
		User user = (User) session.getAttribute("currentUser");
		if (article.getAuthor().getUserId() == user.getUserId())
		{
			return true;
		}
		return false;

	}

	
	/****
	 * 检查是否是admin角色
	 * @return
	 */
	private boolean isAdmin()
	{
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		User user = (User) session.getAttribute("currentUser");
		for (Role r : user.getRoles())
		{
			String role = r.getRole();
			if ("admin".equals(role))
			{
				System.out.println("This user is admin!");
				return true;
			}
		}
		return false;
	}

}
