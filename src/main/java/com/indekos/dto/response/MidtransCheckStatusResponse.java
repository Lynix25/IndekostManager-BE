package com.indekos.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
public class MidtransCheckStatusResponse {

    @JsonProperty("masked_card")
    private String maskedCard;
    @JsonProperty("approval_code")
    private String approvalCode;
    private String bank;
    private String eci;
    @JsonProperty("channel_response_code")
    private String channelResponseCode;
    @JsonProperty("channel_response_message")
    private String channelResponseMessage;
    @JsonProperty("transaction_time")
    private String transactionTime;
    @JsonProperty("gross_amount")
    private String grossAmount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("order_id")
    private String orderId;
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("signature_key")
    private String signatureKey;
    @JsonProperty("status_code")
    private String statusCode;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("transaction_status")
    private String transactionStatus;
    @JsonProperty("fraud_status")
    private String fraudStatus;
    @JsonProperty("settlement_time")
    private String settlementTime;
    @JsonProperty("status_message")
    private String statusMessage;
    @JsonProperty("merchant_id")
    private String merchantId;
    @JsonProperty("card_type")
    private String cardType;
    @JsonProperty("three_ds_version")
    private String threeDsVersion;
    @JsonProperty("challenge_completion")
    private String challengeCompletion;

//    public MidtransCheckStatusResponse(JSONObject object){
//        transactionTime = object.getString("transaction_time");
//        paymentType = object.getString("payment_type");
//    }
}
