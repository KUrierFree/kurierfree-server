package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
