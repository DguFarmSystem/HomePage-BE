package org.farmsystem.homepage.domain.user.repository;

import org.farmsystem.homepage.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{}
