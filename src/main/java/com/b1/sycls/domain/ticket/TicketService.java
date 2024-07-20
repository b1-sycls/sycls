package com.b1.sycls.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TicketService {

    private final TicketAdapter ticketAdapter;
}