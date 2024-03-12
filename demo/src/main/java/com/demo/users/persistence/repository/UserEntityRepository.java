package com.demo.users.persistence.repository;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.users.persistence.entity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Long>{



    
    UserEntity  findByNameAndSecret(String name , String credential);
    
    
    UserEntity  findByName(String name );
    
}   
