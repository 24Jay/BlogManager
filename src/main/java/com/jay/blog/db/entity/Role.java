package com.jay.blog.db.entity;

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
@Table(name = "role")
public class Role
{
	@Id
	@GeneratedValue
	@Column(name = "role_id")
	private Long roleId; // 编号

	@Column(name = "role")
	private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:

	@Column(name = "descrition")
	private String description;

	// 角色 -- 权限关系：多对多关系;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_permission", joinColumns = {
			@JoinColumn(name = "rid", referencedColumnName = "role_id") }, inverseJoinColumns = {
					@JoinColumn(name = "pid", referencedColumnName = "permission_id") })
	private Set<Permission> permissions;


	public Long getRoleId()
	{
		return roleId;
	}

	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}


	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Set<Permission> getPermissions()
	{
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions)
	{
		this.permissions = permissions;
	}

	

	public String toString()
	{
		return "[ roleId=" + roleId + ", role=" + role + "]";
	}
	
}
