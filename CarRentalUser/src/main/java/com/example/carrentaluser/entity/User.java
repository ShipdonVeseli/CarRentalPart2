package com.example.carrentaluser.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class User {


    //vorübergehend hinzugefügt damit es keine error gingt
    public String getUsername() {
        return "";
    }

    //vorübergehend hinzugefügt damit es keine error gingt
    public String getPassword() {
        return "";
    }
}
