package com.b1.ticket.dto;

import com.b1.common.PageResponseDto;
import com.b1.content.entity.ContentStatus;
import com.b1.round.entity.RoundStatus;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.ticket.entity.TicketStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TicketGetAllUserResponseDto {

    private Long contentId;
    private String contentTitle;
    private ContentStatus contentStatus;
    private Long roundId;
    private Integer sequence;
    private List<String> seatGradeCode;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private RoundStatus roundStatus;
    private Long ticketId;
    private Integer price;
    private TicketStatus ticketStatus;

    public static PageResponseDto<TicketGetAllUserResponseDto> of(
            final Page<TicketGetAllDto> ticketDtoPage,
            final Set<SeatGrade> seatGrades
    ) {
        Page<TicketGetAllUserResponseDto> responseDtoPage = ticketDtoPage.map(ticketDto -> from(ticketDto, seatGrades));
        return PageResponseDto.of(responseDtoPage);
    }

    private static TicketGetAllUserResponseDto from(TicketGetAllDto dto, Set<SeatGrade> seatGrades) {
        List<String> seatGradeCodes = seatGrades.stream()
                .filter(sg -> sg.getTicketId().equals(dto.getTicketId()))
                .map(sg -> sg.getSeat().getCode())
                .collect(Collectors.toList());

        return TicketGetAllUserResponseDto.builder()
                .contentId(dto.getContentId())
                .contentTitle(dto.getContentTitle())
                .contentStatus(dto.getContentStatus())
                .roundId(dto.getRoundId())
                .sequence(dto.getSequence())
                .seatGradeCode(seatGradeCodes)
                .startDate(dto.getStartDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .roundStatus(dto.getRoundStatus())
                .ticketId(dto.getTicketId())
                .price(dto.getPrice())
                .ticketStatus(dto.getTicketStatus())
                .build();
    }

}
