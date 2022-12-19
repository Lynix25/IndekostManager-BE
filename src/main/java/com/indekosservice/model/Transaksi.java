package com.indekosservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class Transaksi {
    @Id
    private String transaksiID;
    private Long cratedAt;
    private Long resolveAt;
    private String requesterUser;
    private String category;
    private String description;
}
