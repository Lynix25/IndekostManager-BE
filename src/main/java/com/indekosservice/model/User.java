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
    private String accountID;
    private String name;
    private String phone;
//    private String pekerjaan;
//    private String gender;
//    @JsonProperty(value = "is_deleted")
//    private Boolean isDeleted;
//    @JsonProperty(value = "created_by")
//    private String createdBy;
//    @JsonProperty(value = "created_date")
//    private Long createdDate;
//    @JsonProperty(value = "last_modified_by")
//    private String lastModifiedBy;
//    @JsonProperty(value = last_modified_date)
//    private Long lastModifiedDate;
}
