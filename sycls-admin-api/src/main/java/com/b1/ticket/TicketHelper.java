package com.b1.ticket;

import com.b1.ticket.entity.Ticket;
import com.b1.ticket.entity.TicketStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Ticket Helper")
@Component
@RequiredArgsConstructor
public class TicketHelper {

    private final TicketRepository ticketRepository;


    public List<Ticket> getAllTicketByRoundIdAndReserved(Long roundId) {
        return ticketRepository.findAllByRoundId(roundId).stream()
                .filter(ticket -> TicketStatus.isReserved(ticket.getStatus()))
                .toList();
    }
}
