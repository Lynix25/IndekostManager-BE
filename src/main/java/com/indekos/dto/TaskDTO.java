package com.indekos.dto;

import com.indekos.model.Room;
import com.indekos.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDTO {
    private Task task;
    private Room room;
}
