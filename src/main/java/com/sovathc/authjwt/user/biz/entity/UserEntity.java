package com.sovathc.authjwt.user.biz.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name= UserEntity.TABLE_NAME)
public class UserEntity{
    public static final String TABLE_NAME = "users";
    @Id 
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String username;
    private String password;
    private String description;
    
}
