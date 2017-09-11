package com.jay.blog.db.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jay.blog.db.entity.User;


public interface UserJPA extends CrudRepository<User, Long>
{
    public User findByUserName(String username);
  
    @Query ("select userId, userName from User")
    public Iterable<User> findAlls();
    
}
