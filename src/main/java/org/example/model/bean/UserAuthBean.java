package org.example.model.bean;

import lombok.Data;

import java.util.Date;

@Data
public class UserAuthBean {

    private String username;

    private Date expirationDate;

    private String token;

    @Override
    public String toString() {
        return "{" +
                "\"username\": " + "\"" + username + "\"," +
                "\"expirationDate\" :" + "\"" + expirationDate + "\"," +
                "\"token\": " + "\"" + token + '\"' +
                '}';
    }
}
