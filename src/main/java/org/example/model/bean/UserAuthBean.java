package org.example.model.bean;

import lombok.Data;
import org.example.model.dto.AbstractDTO;

import java.util.Date;
import java.util.UUID;

@Data
public class UserAuthBean extends AbstractDTO {

    private UUID id;

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
