package com.indekos.model;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;


@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Account extends AuditableEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
