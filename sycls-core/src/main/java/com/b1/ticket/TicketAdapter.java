package com.b1.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketAdapter {

    private final TicketRepository ticketRepository;
}
