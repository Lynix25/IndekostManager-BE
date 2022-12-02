package com.indekosservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
