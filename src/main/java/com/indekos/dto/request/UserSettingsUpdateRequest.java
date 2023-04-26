package com.indekos.dto.request;

import lombok.Getter;

@Getter
public class UserSettingsUpdateRequest {
    private Boolean shareRoom;
    private Boolean enableNotification;
}
