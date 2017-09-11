package com.jay.blog.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Permission implements Serializable
{
	@Id
	@GeneratedValue
	@Column(name = "permission_id")
	private Long permissionId;// 主键.

	@Column(name = "permission_name")
	private String permissionName;// 名称.
	@Column(name = "resource_type", columnDefinition = "enum('view','edit')")
	private String resourceType;

	@Column(name = "url")
	private String url;

	@Column(name = "permission")
	private String permission; 


	public Long getPermissionId()
	{
		return permissionId;
	}

	public void setPermissionId(Long permissionId)
	{
		this.permissionId = permissionId;
	}

	public String getPermissionName()
	{
		return permissionName;
	}

	public void setPermissionName(String permissionName)
	{
		this.permissionName = permissionName;
	}

	public String getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getPermission()
	{
		return permission;
	}

	public void setPermission(String permission)
	{
		this.permission = permission;
	}


}
