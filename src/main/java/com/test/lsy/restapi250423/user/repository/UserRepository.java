package com.test.lsy.restapi250423.user.repository;

import com.test.lsy.restapi250423.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
