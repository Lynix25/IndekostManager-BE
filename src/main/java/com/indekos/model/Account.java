package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.BaseEntity;

import com.indekos.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor @AllArgsConstructor
public class Account extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;
    
    private Long loginTime;
    
    private Long logoutTime;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public boolean authorized(String password){
        return this.password.compareTo(Utils.passwordHashing(password)) == 0 ? true : false;
    }
}