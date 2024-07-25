package com.b1.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketHelper {

    private final TicketRepository ticketRepository;

}
