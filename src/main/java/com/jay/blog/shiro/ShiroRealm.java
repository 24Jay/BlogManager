package com.jay.blog.shiro;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.jay.blog.db.entity.Permission;
import com.jay.blog.db.entity.Role;
import com.jay.blog.db.entity.User;
import com.jay.blog.db.jpa.UserJPA;

/***
 * 
 * Realm：可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全实体的<br>
 * 可以是JDBC实现，也可以是LDAP实现，或者内存实现等等<br>
 * 由用户提供<br>
 * 注意：Shiro不知道你的用户/权限存储在哪及以何种格式存储<br>
 * 所以我们一般在应用中都需要实现自己的Realm
 * 
 * @author jay
 *
 */
public class ShiroRealm extends AuthorizingRealm
{

	@Autowired
	private UserJPA userJPA;
	
	
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		System.out.println("权限配置----------->ShiroRealm.doGetAuthorizationInfo");
		String username = (String) principals.getPrimaryPrincipal();
		User user = userJPA.findByUserName(username);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		for (Role role : user.getRoles())
		{
			authorizationInfo.addRole(role.getRole());
			for (Permission p : role.getPermissions())
			{
				authorizationInfo.addStringPermission(p.getPermission());
			}
		}

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&" + authorizationInfo);
		return authorizationInfo;
	}

	/* 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
	{

		System.out.println("\nShiroRealm.doGetAuthenticationInfo()---->>验证用户名密码：");
		/***
		 * principal即身份,credential即证明(凭证)，例如密码<br>
		 * 最常见的principal/credential组合就是用户名/密码<br>
		 */
		// 这里就是用户登录提供的用户名和密码
		String username = (String) token.getPrincipal();
		String passwd = new String(((char[]) token.getCredentials()));


		// 下面就是从数据库中取出来的用户名密码
		User user = userJPA.findByUserName(username);
		if (user == null || (!user.getUserName().equals(username)))
		{
			throw new UnknownAccountException();
		}
		else if (!user.getPassword().equals(passwd))
		{
			throw new IncorrectCredentialsException();
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, passwd, getName());
		this.setAuthenticationSession(user);
		System.out.println("authenticationInfo------->" + authenticationInfo.toString());
		return authenticationInfo;
	}

	/**
	 * 将一些数据放到ShiroSession中，以便于其它地方使用
	 * 比如Controller里面，使用时直接用HttpSession.getAttribute(key)就可以取到
	 */
	private void setAuthenticationSession(Object value)
	{
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser)
		{
			Session session = currentUser.getSession();
			session.setTimeout(1000 * 60 * 60 * 2);
			session.setAttribute("currentUser", value);
		}
	}

}
