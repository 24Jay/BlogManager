package com.jay.blog.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jay.blog.Response;
import com.jay.blog.db.entity.User;
import com.jay.blog.db.jpa.UserJPA;

@RestController
@RequestMapping("/user")
public class UserController
{

	@Autowired
	private UserJPA userJPA;

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@RequiresRoles("admin")
	public @ResponseBody User add(@RequestBody User user)
	{
		return userJPA.save(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@RequiresRoles(value = { "admin", "user" }, logical = Logical.OR)
	public @ResponseBody Iterable<User> get()
	{
		// return userJPA.findAll();
		return userJPA.findAlls();
	}

	// @RequiresPermissions("user:view") // 权限管理;
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@RequiresRoles("admin")
	public @ResponseBody User getById(@PathVariable Long id)
	{
		return userJPA.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@RequiresRoles("admin")
	public @ResponseBody User put(@RequestBody User user)
	{
		return userJPA.save(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@RequiresRoles("admin")
	public @ResponseBody Response delete(@PathVariable Long id)
	{
		if (!userJPA.exists(id))
			return new Response(404, Response._404);
		userJPA.delete(id);
		if (!userJPA.exists(id))
			return new Response(200, Response._200);
		else
			return new Response(501, Response._501);

	}

}
