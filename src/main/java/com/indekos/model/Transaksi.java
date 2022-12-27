package com.indekos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

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
