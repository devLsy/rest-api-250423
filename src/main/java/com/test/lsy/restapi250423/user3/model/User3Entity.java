package com.test.lsy.restapi250423.user3.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_info_250514")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class User3Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public User3Entity(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
