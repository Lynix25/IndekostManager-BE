package com.indekos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.indekos.common.base.entity.AuditableEntity;
import com.indekos.utils.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Transaction extends AuditableEntity {
	private static final long serialVersionUID = 1L;
	
	private Long penaltyFee;
    private String token;

    private String paymentId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Task> taskItems;

    @OneToMany(mappedBy = "transaction" ,cascade = CascadeType.ALL)
    private List<Rent> rentItems;

    public Integer getTotalItem(){
        Integer total = 0;
        if(taskItems != null) total+= taskItems.size();
        if(rentItems != null) total+= rentItems.size();
        return total;
    }

    public Long getTotalPrice(){
        Long totalPrice = 0L;
        if(taskItems != null) {
            for (Task task: taskItems) {
                totalPrice += task.getCharge();
            }
        }
        if(rentItems != null) {
            for (Rent rent: rentItems) {
                totalPrice += rent.getPrice();
            }
        }
        return totalPrice;
    }
}
