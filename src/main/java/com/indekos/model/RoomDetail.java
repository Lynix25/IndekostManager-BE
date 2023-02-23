package com.indekos.model;

import com.indekos.common.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class RoomDetail extends BaseEntity {
    private String name;
    private String description;
    private String category;
}
