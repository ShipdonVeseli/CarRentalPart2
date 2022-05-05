package com.example.carrentaluser.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document("users")
@Getter
@Setter
public class User implements Serializable {
    @Id
    private String id;

    private String username;
    private String password;

    public User(String id, String username, String password) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
