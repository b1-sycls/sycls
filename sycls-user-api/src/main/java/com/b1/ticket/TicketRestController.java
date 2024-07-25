package com.b1.ticket;

import com.b1.globalresponse.RestApiResponseDto;
import com.b1.ticket.dto.TicketAddRequestDto;
import com.b1.ticket.dto.TicketAddResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TicketRestController {

    private final TicketService ticketService;

    @PostMapping("/tickets")
    public ResponseEntity<RestApiResponseDto<TicketAddResponseDto>> addTicket(
            @Valid @RequestBody final TicketAddRequestDto requestDto
    ) {
        TicketAddResponseDto responseDto = ticketService.addTicket(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of("해당 좌석이 선택되었습니다.", responseDto));
    }
}
