package com.indekos.dto;

import com.indekos.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private com.indekos.model.User user;
    private Room room;
}
