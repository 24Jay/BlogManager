### 博客管理系统
- Restful服务端接口 : 用户管理(增删查改), 文章管理(增删查改)
- 基于spring boot + shiro + JPA


#### 第一部分 : 使用说明
> 本文主要包含三部分内容：第一部分为使用说明,第二部分为系统用户账户管理接口,第三部分为博客文章管理接口.
> 代码基于spring-boot框架, 配合使用shiro进行用户权限验证, 使用JPA进行数据库操作, 数据库为mysql. 以下测试基于ubuntu16.4系统, 测试工具为Postman.
> 系统启动方法, 命令行运行jar文件即可, 代码内置默认端口为31547(不提供用户配置方式):
```python
java -jar blog-0.0.1-SNAPSHOT.jar
```
-----

#### 第二部分 : 用户账户管理管理
> 主要包括用户账号的增删查改,以下所有的操作只能是拥有admin角色用户能够使用,普通用户user无法操作.
> ##### 1.0 登录系统
```
Url : http://localhost:31547/login 
Method : Post
Body(Form-data) : userName=admin, Password=123456
```
> 
> ##### 1.1新增用户
```
Url : http://localhost:31547/user
Method : Post
Body :
{

    "userId": 3,

    "userName": "Jim",

    "password": "123456",

    "email": "admin@gmail.com",

    "roles": [

    {

            "roleId": 1,

            "role": "admin",

            "description": "Manage all resources",

            "permissions": []

        
    },

    {

            "roleId": 2,

            "role": "user",

            "description": "manage own articles",

            "permissions": []

        
    }

    
    ]


}

Response:

{

    "userId": 29,

    "userName": "Jim",

    "password": "123456",

    "email": "admin@gmail.com",

    "roles": [

    {

            "roleId": 1,

            "role": "admin",

            "description": "Manage all resources",

            "permissions": []

        
    },

    {

            "roleId": 2,

            "role": "user",

            "description": "manage own articles",

            "permissions": []

        
    }

    
    ]

}
```
> ##### 1.2 修改用户信息
```
Url: http://localhost:31547/user/29
Method: Put
Body:
{

    "userId": 29,

    "userName": "Jim",

    "password": "123456",

    "email": "jim@gmail.com",

    "roles": [

    {

            "roleId": 2,

            "role": "user",

            "description": "manage own articles",

            "permissions": []

        
    }

    
    ]


}

Response:
{

    "userId": 29,

    "userName": "Jim",

    "password": "123456",

    "email": "jim@gmail.com",

    "roles": [

    {

            "roleId": 2,

            "role": "user",

            "description": "manage own articles",

            "permissions": []

        
    }

    ]
}
```
> 
> ##### 1.3 删除用户账号
```
Url: http://localhost:31547/user/29r
Method: Delete
Response:
{

    "status": 200,

    "message": "Operation Succeed!",

    "result": null
}
```
> ##### 1.4 根据id查询用户信息
```
Url: http://localhost:31547/user/1
Method: Get
Response:
{

    "userId": 1,

    "userName": "admin",

    "password": "123456",

    "email": "admin@gmail.com",

    "roles": [

    {

            "roleId": 2,

            "role": "user",

            "description": "manage own articles",

            "permissions": []

        
    },

    {

            "roleId": 1,

            "role": "admin",

            "description": "Manage all resources",

            "permissions": []

        
    }

    
    ]


}
```
> ##### 1.5普通用户无权限操作
```html
<!DOCTYPE html>

<html lang="en">

    <head>

        <meta charset="UTF-8" />

        <title>Title</title>

    </head>

    <body>

        <h3>401:您没有该操作权限！</h3>

    </body>

</html>
```
-----



#### 第三部分 : 博客文章管理
> ##### 2.1　查询所有文章
```
Url: http://localhost:31547/article
Method: Get
Response:
[
{

        "id": 3,

        "title": "linux",

        "content": "linux基础和进阶知识...鸟哥的私房菜",

        "authorId": 2

    
},

{

        "id": 4,

        "title": "spring",

        "content": "spring实战,spring boot, spring mvc",

        "authorId": 24

    
},

{

        "id": 5,

        "title": "spring",

        "content": "spring实战,spring boot",

        "authorId": 1

    
}


]

```
> ##### 2.2　根据id查询文章
```
Url: http://localhost:31547/article/3
Method: Get
Response:
{

    "id": 3,

    "title": "linux",

    "content": "linux基础和进阶知识...鸟哥的私房菜",

    "authorId": 2

}
```
> ##### 2.3 新增文章
```
Url: http://localhost:31547/article
Method: Post
Body:
{

    "id": 26,

    "title": "Netty权威指南",

    "content": "java bio, nio, aio",

    "authorId": 1
}

Response:
{

    "status": 200,

    "message": "Operation Succeed!",

    "result": {

        "id": 26,

        "title": "Netty权威指南",

        "content": "java bio, nio, aio",

        "authorId": 1

    
    }


}
```
> ##### 2.3 修改文章
```
Url: http://localhost:31547/article/27
Method: Put
Body:
{

    "id": 27,

    "title": "Netty权威指南",

    "content": "java bio, nio, aio,服务器端编程. SocketServer",

    "authorId": 3

}

Response:
{

    "status": 200,

    "message": "Operation Succeed!",

    "result": {

        "id": 27,

        "title": "Netty权威指南",

        "content": "java bio, nio, aio,服务器端编程. SocketServer",

        "authorId": 3

    
    }

}
```
> ##### 2.4 删除文章
```
Url: http://localhost:31547/article/2
Method: Delete
Response:
{

    "status": 200,

    "message": "Operation Succeed!",

    "result": null
}

```
