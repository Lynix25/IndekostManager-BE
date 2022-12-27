package com.indekos.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "account")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id
    private String accoutID;
    private String username;
    private String password;
    private String userID;

}
