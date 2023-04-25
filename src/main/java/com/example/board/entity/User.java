package com.example.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="user")
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}


