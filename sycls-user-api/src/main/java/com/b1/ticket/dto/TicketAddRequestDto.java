package com.b1.ticket.dto;

import com.b1.constant.DomainConstant;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record TicketAddRequestDto(
        @NotNull(message = "공얀 회차 정보를 입력해 주세요")
        Long roundId,
        @NotNull(message = "좌석 정보가 누락되었습니다.")
        @Size(max = DomainConstant.MAXIMUM_SELECTED_SEAT, message = "최대 좌석 수를 초과하였습니다.")
        Set<Long> seatGradeIds
) {

}
