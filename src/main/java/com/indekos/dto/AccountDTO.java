package com.indekos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data @AllArgsConstructor
public class AccountDTO  {
	private String id;
	private String username;
}
