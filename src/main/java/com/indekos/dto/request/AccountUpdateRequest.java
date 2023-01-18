package com.indekos.dto.request;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
public class AccountUpdateRequest extends AuditableRequest {
}
