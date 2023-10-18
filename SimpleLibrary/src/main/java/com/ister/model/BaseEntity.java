package com.ister.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class BaseEntity<PK> {

    @Id
    private PK id;
    private String name;
}
