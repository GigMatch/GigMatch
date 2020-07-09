package com.gigmatch.demo.daos;

import com.gigmatch.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
