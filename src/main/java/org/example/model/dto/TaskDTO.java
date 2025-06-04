package org.example.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDTO extends AbstractDTO{

    private UUID id;

    private UserDTO user;

    private String description;

    private Date initDate;
}
