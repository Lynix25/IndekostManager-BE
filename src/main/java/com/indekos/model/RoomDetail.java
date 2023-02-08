package com.indekos.model;

import com.indekos.common.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class RoomDetail extends BaseEntity {
    private Integer capacity;
    private Integer price;

//    @ManyToOne
//    private Room room;
}
