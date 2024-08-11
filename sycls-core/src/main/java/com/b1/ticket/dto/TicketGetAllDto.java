package com.b1.ticket.dto;

import com.b1.content.entity.ContentStatus;
import com.b1.round.entity.RoundStatus;
import com.b1.ticket.entity.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class TicketGetAllDto {
    private final Long contentId;
    private final String contentTitle;
    private final ContentStatus contentStatus;
    private final Long roundId;
    private final Integer sequence;
    private final LocalDate startDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final RoundStatus roundStatus;
    private final Long ticketId;
    private final Integer price;
    private final TicketStatus ticketStatus;
}
