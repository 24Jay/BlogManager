package com.jay.blog.db.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name = "user_id", unique = true)
	private Long userId;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "email")
	private String email;

	// 立即从数据库中进行加载数据;
	@ManyToMany(fetch = FetchType.EAGER)
	// 定义用户和角色关系(use-role):一个用户对应多个角色
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "user_id") }, inverseJoinColumns = {
					@JoinColumn(name = "rid", referencedColumnName = "role_id") })
	private Set<Role> roles;

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public String toString()
	{
		return "[ userId=" + userId + ", userName=" + userName + ", password=" + password + "]";
	}

}
