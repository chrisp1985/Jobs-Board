package com.chrisp1985.jobsboard_backend.repository;

import com.chrisp1985.jobsboard_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
