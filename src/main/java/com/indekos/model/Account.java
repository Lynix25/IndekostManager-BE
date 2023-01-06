package com.indekos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.indekos.common.base.entity.AuditableEntity;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "account")
@NoArgsConstructor @AllArgsConstructor
public class Account extends AuditableEntity {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String userID;

}
