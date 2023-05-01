package com.indekos.dto.request;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class PaymentNotificationRequest {
    private String transaction_time;
    private String transaction_status;
    private String transaction_id;
    private String status_message;
    private String status_code;
    private String signature_key;
    private String payment_type;
    private String order_id;
    private String merchant_id;
    private String masked_card;
    private String gross_amount;
    private String fraud_status;
    private String eci;
    private String currency;
    private String channel_response_message;
    private String channel_response_code;
    private String card_type;
    private String bank;
    private String approval_code;
    private String settlement_time;
    private String transaction_type;
    private String acquirer;
    private String permata_va_number;
    private List<Map<String,Object>> va_numbers;
    private List<Map<String,Object>> payment_amounts;
    private String biller_code;
    private String bill_key;
    private String store;
    private String payment_code;
    private String expiry_time;
}
