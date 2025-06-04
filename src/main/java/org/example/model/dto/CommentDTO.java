package org.example.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.model.entity.User;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class CommentDTO extends AbstractDTO{

    private UUID id;

    private UserDTO userSender;

    private String message;

    private Date inclusionDate;

    private boolean edited;

    private Date editionDate;

}
