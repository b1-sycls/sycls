package com.b1.ticket.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketStatus {
    RESERVED("RESERVED"),        // 예매 완료
    USED("USED"),            // 사용 완료
    REFUNDED_PERSONAL("REFUNDED_PERSONAL"), // 환불 (개인 사유)
    REFUNDED_CANCELED("REFUNDED_CANCELED"), // 환불 (공연 취소)
    ;

    private final String value;
}
