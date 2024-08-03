package com.b1.payment.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class TossPaymentRestResponse {
    private String country;
    private String orderId;
    private Boolean isPartialCancelable;
    private String lastTransactionKey;
    private Integer taxExemptionAmount;
    private Integer suppliedAmount;
    private String secret;
    private String type;
    private Boolean cultureExpense;
    private Integer taxFreeAmount;
    private OffsetDateTime requestedAt;
    private String currency;
    private String paymentKey;
    private Checkout checkout;
    private String orderName;
    private String method;
    private Boolean useEscrow;
    private Integer vat;
    private String mId;
    private OffsetDateTime approvedAt;
    private Integer balanceAmount;
    private String version;
    private EasyPay easyPay;
    private Integer totalAmount;
    private Receipt receipt;
    private String status;

    // Inner classes
    @Data
    public static class Checkout {
        private String url;
    }

    @Data
    public static class EasyPay {
        private Integer amount;
        private String provider;
        private Integer discountAmount;
    }

    @Data
    public static class Receipt {
        private String url;
    }
}

/*
 {
    "country": "KR",
    "orderId": "MC4xMjEyNjIxOTcyOTEx",
    "cashReceipts": null,
    "isPartialCancelable": true,
    "lastTransactionKey": "CDF5FF1362C544B4E46067F47D4403DD",
    "discount": null,
    "taxExemptionAmount": 0,
    "suppliedAmount": 454545,
    "secret": "ps_5OWRapdA8dj0mp5Z9LmXVo1zEqZK",
    "type": "NORMAL",
    "cultureExpense": false,
    "taxFreeAmount": 0,
    "requestedAt": "2024-08-02T01:49:36+09:00",
    "currency": "KRW",
    "paymentKey": "tgen_20240802014936MNbu8",
    "virtualAccount": null,
    "checkout": {
        "url": "https://api.tosspayments.com/v1/payments/tgen_20240802014936MNbu8/checkout"
    },
    "orderName": "서윤조이스",
    "method": "간편결제",
    "useEscrow": false,
    "vat": 45455,
    "mId": "tgen_docs",
    "approvedAt": "2024-08-02T01:49:47+09:00",
    "balanceAmount": 500000,
    "version": "2022-11-16",
    "easyPay": {
        "amount": 500000,
        "provider": "카카오페이",
        "discountAmount": 0
    },
    "totalAmount": 500000,
    "cancels": null,
    "transfer": null,
    "mobilePhone": null,
    "failure": null,
    "receipt": {
        "url": "https://dashboard.tosspayments.com/receipt/redirection?transactionId=tgen_20240802014936MNbu8&ref=PX"
    },
    "giftCertificate": null,
    "cashReceipt": null,
    "card": null,
    "status": "DONE"
}
 */
