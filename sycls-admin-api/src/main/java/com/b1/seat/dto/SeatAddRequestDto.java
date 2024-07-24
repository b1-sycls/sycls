package com.b1.seat.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record SeatAddRequestDto(
        @NotEmpty(message = "좌석코드가 누락되었습니다.")
        List<@Size(max = 10, message = "최대 10자까지 입력가능합니다.") String> codeList
) {

}
