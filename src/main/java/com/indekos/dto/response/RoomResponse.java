package com.indekos.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
({
	"id", "name", "description",
	"quota", "remainingQuota"
})
public interface RoomResponse {
	
	String getId();
	String getName();
	String getDescription();
	int getQuota();
	int getRemainingQuota();
}
