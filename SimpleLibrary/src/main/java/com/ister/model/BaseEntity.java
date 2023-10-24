package com.ister.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BaseEntity<PK> {

    @Id
    private PK id;
    private String name;
}
