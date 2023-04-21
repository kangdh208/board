package com.example.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
}


