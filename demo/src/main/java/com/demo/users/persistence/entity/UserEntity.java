package com.demo.users.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class UserEntity {
    

    @Id
    @GeneratedValue(strategy  =  GenerationType.IDENTITY)
    private  Long idUser;

    private  String name;

    private  String secret;

    private String  role ;

    private String desc;


}
