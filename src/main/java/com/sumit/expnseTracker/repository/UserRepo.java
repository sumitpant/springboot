package com.sumit.expnseTracker.repository;

import com.sumit.expnseTracker.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<UserEntity,String> {
}
