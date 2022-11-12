package com.sovathc.authjwt.user.biz.service.impl;

import com.sovathc.authjwt.user.biz.entity.UserEntity;
import com.sovathc.authjwt.user.biz.repository.UserRepository;
import com.sovathc.authjwt.user.biz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private final UserRepository repository;

    @Override
    public UserEntity findAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = repository.findByUsername(authentication.getName());

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = repository.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
