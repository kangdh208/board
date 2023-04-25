package com.example.board.repository;

import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
