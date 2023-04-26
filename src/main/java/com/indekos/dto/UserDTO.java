package com.indekos.dto;

import com.indekos.model.Room;
import com.indekos.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private User user;
    private Room room;
}
