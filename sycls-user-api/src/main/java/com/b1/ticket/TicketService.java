package com.b1.ticket;

import com.b1.cast.CastHelper;
import com.b1.cast.entity.dto.CastGetUserResponseDto;
import com.b1.common.PageResponseDto;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.ticket.dto.TicketGetAllDto;
import com.b1.ticket.dto.TicketGetAllUserResponseDto;
import com.b1.ticket.dto.TicketGetDetailDto;
import com.b1.ticket.dto.TicketGetDetailUserResponseDto;
import com.b1.user.entity.User;
import com.b1.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j(topic = "Ticket Service")
@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketHelper ticketHelper;
    private final SeatGradeHelper seatGradeHelper;
    private final CastHelper castHelper;

    /**
     * 티켓 페이징 조회
     */
    public PageResponseDto<TicketGetAllUserResponseDto> getAllTickets(
            final User user,
            final Integer pageNum
    ) {

        PageUtil.checkPageNumber(pageNum);

        Pageable pageable = PageRequest.of(pageNum - 1, 10);

        Page<TicketGetAllDto> ticketDto = ticketHelper.getAllTicketForUser(user, pageable);
        List<Long> ticketIds = ticketDto.getContent().stream()
                .map(TicketGetAllDto::getTicketId)
                .toList();

        Set<SeatGrade> seatGrades = seatGradeHelper.getAllSeatGradesByTicketId(ticketIds);

        return TicketGetAllUserResponseDto.of(ticketDto, seatGrades);
    }

    /**
     * 티켓 상세 조회
     */
    public TicketGetDetailUserResponseDto getDetailTicket(
            final Long ticketId
    ) {
        TicketGetDetailDto ticketGetDetailDto = ticketHelper.getDetailTicketForUser(ticketId);
        Set<SeatGrade> seatGrades = seatGradeHelper.getSeatGradesByTicketId(ticketGetDetailDto.getTicketId());
        List<CastGetUserResponseDto> castsDto = castHelper.getAllCastsByRoundId(ticketGetDetailDto.getRoundId());

        return TicketGetDetailUserResponseDto.of(ticketGetDetailDto, seatGrades, castsDto);
    }
}
