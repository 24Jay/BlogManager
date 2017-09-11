package com.jay.blog.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HomeController
{
	@RequestMapping({ "/", "/index" })
	public String index()
	{
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login()
	{
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password, Map<String, Object> map)
			throws Exception
	{
		System.out.println("\nHomeController-----POSTTTTTTTT------userName: " + userName);
		System.out.println("HomeController----POSTTTTTTTT------passwd: " + password);

		try
		{

			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			token.setRememberMe(true);
			System.out.println("HomeController------->token=" + token);
			/***
			 * 调用SecurityManager进行登录认证
			 */
			subject.login(token);
			System.out.println("HomeController------->successfully!");
			return "index";
		}
		catch (Exception e)
		{
			System.out.println("Authorization Failed: " + e);
			map.put("msg", "用户名或密码错误，请重新输入！");
			return "login";
		}

	}

	@RequestMapping("/403")
	public String unauthorizedRole()
	{
		System.out.println("------没有权限-------");
		return "403";
	}

}