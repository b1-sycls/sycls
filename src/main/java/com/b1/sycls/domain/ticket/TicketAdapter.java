package com.b1.sycls.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketAdapter {

    private final TicketRepository ticketRepository;
}
