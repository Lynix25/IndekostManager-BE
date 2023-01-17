package com.indekos.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "service")
public class Service {
    @Id
    private String id;
    private String serviceName;
    private String variant;
    private String price;
}
