package com.indekos.dto.request;

import lombok.Data;
import lombok.Getter;

@Getter
public class NotificationCrateRequest {
    private String targetedUserId;
    private String message;
    private String title;
    private String redirect;
    private String category;
}
