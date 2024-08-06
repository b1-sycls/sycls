package com.b1.ticket;

import com.b1.ticket.dto.TicketGetAllDto;
import com.b1.ticket.dto.TicketGetDetailDto;
import com.b1.ticket.entity.Ticket;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Ticket Helper")
@Component
@RequiredArgsConstructor
public class TicketHelper {
    private final TicketQueryRepository ticketQueryRepository;
    private final TicketRepository ticketRepository;

    public Ticket addTicket(
            final Ticket ticket
    ) {
        return ticketRepository.save(ticket);
    }

    /**
     * 티켓 페이징 조회
     */
    public Page<TicketGetAllDto> getAllTicketForUser(
            final User user,
            final Pageable pageable
    ) {
        return ticketQueryRepository.getAllTicketForUser(user, pageable);
    }

    public TicketGetDetailDto getDetailTicketForUser(
            final Long ticketId
    ) {
        return ticketQueryRepository.getDetailTicketForUser(ticketId);
    }
}
