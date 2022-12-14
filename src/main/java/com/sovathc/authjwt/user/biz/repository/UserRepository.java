package com.sovathc.authjwt.user.biz.repository;

import com.sovathc.authjwt.user.biz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
}
