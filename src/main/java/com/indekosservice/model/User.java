package com.indekosservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class User {
    @Id
    private String userID;
    private String name;
    private String noTelp;
    private String accountID;
}
