package com.b1.ticket;

import com.b1.common.PageResponseDto;
import com.b1.seatgrade.SeatGradeHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.ticket.dto.TicketGetAllDto;
import com.b1.ticket.dto.TicketGetAllUserResponseDto;
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

    private final TicketQueryRepository ticketQueryRepository;
    private final SeatGradeHelper seatGradeHelper;

    /**
     * 티켓 페이징 조회
     */
    public PageResponseDto<TicketGetAllUserResponseDto> getAllTickets(
            final User user,
            final Integer pageNum
    ) {

        PageUtil.checkPageNumber(pageNum);

        Pageable pageable = PageRequest.of(pageNum - 1, 10);
        Page<TicketGetAllDto> ticketDto = ticketQueryRepository.getAllTicketForUser(user, pageable);

        List<Long> ticketIds = ticketDto.getContent().stream()
                .map(TicketGetAllDto::getTicketId)
                .toList();

        Set<SeatGrade> seatGrades = seatGradeHelper.getAllSeatGradesByTicketId(ticketIds);

        return TicketGetAllUserResponseDto.of(ticketDto, seatGrades);
    }
}
