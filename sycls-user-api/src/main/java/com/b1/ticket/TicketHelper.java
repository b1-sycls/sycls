package com.b1.ticket;

import com.b1.ticket.entity.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Ticket Helper")
@Component
@RequiredArgsConstructor
public class TicketHelper {
    private final TicketRepository ticketRepository;

    public Ticket addTicket(
            final Ticket ticket
    ) {
        return ticketRepository.save(ticket);
    }
}
