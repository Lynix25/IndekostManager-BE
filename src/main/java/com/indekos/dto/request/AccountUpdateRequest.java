package com.indekos.dto.request;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class AccountUpdateRequest extends AuditableRequest {
    private String password;

}
