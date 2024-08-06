package com.b1.ticket;

import com.b1.common.PageResponseDto;
import com.b1.globalresponse.RestApiResponseDto;
import com.b1.security.UserDetailsImpl;
import com.b1.ticket.dto.TicketGetAllUserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TicketRestController {
    private final TicketService ticketService;

    /**
     * 티켓 페이징 조회
     */
    @GetMapping("/tickets")
    public ResponseEntity<RestApiResponseDto<PageResponseDto<TicketGetAllUserResponseDto>>> getAllTickets(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") final Integer pageNum
    ) {
        PageResponseDto<TicketGetAllUserResponseDto> responseDto = ticketService
                .getAllTickets(userDetails.getUser(), pageNum);

        return ResponseEntity.status(HttpStatus.OK)
                .body(RestApiResponseDto.of(responseDto));
    }
}
