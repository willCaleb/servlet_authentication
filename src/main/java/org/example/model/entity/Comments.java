package org.example.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.annotation.Personal;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Personal
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userSender;

    @Column(name = "message")
    private String message;

    @Column(name = "inclusion_date")
    private Date inclusionDate;

    @Column(name = "edited")
    private boolean edited;

    @Column(name = "edition_date")
    private Date editionDate;

}
