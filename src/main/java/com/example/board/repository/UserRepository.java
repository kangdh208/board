package com.example.board.repository;

import com.example.board.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUseridAndPassword(String userid, String password);

    Optional<Users> findByUsername(String username);

    public Boolean existsByUserid(String userid);
}
