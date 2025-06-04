package org.example.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractDTO{

    private UUID id;

    private String username;

    private String password;

}
