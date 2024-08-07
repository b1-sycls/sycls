package com.b1.ticket.dto;

import com.b1.cast.entity.dto.CastGetUserResponseDto;
import com.b1.content.entity.ContentStatus;
import com.b1.round.entity.RoundStatus;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.ticket.entity.TicketStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class TicketGetDetailUserResponseDto {
    private Long contentId;
    private String contentTitle;
    private ContentStatus contentStatus;
    private String mainImagePath;
    private Long roundId;
    private Integer sequence;
    private List<String> seatGradeCode;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private RoundStatus roundStatus;
    private String location;
    private String placeName;
    private Long ticketId;
    private Integer price;
    private TicketStatus ticketStatus;
    private LocalDateTime ticketCreateAt;
    private List<TicketGetDetailCastResponseDto> castResponseDtos;

    public static TicketGetDetailUserResponseDto of(
            final TicketGetDetailDto ticketDto,
            final Set<SeatGrade> seatGrades,
            final List<CastGetUserResponseDto> castDto
    ) {
        List<String> seatGradeCodes = seatGrades.stream()
                .map(sg -> sg.getSeat().getCode())
                .toList();

        return TicketGetDetailUserResponseDto.builder()
                .contentId(ticketDto.getContentId())
                .contentTitle(ticketDto.getContentTitle())
                .contentStatus(ticketDto.getContentStatus())
                .mainImagePath(ticketDto.getMainImagePath())
                .roundId(ticketDto.getRoundId())
                .sequence(ticketDto.getSequence())
                .seatGradeCode(seatGradeCodes)
                .startDate(ticketDto.getStartDate())
                .startTime(ticketDto.getStartTime())
                .endTime(ticketDto.getEndTime())
                .roundStatus(ticketDto.getRoundStatus())
                .location(ticketDto.getLocation())
                .placeName(ticketDto.getPlaceName())
                .ticketId(ticketDto.getTicketId())
                .price(ticketDto.getPrice())
                .ticketStatus(ticketDto.getTicketStatus())
                .ticketCreateAt(ticketDto.getTicketCreateAt())
                .castResponseDtos(TicketGetDetailCastResponseDto.from(castDto))
                .build();
    }

    public void updateMainImagePath(String imagePath) {
        this.mainImagePath = imagePath;
    }
}
