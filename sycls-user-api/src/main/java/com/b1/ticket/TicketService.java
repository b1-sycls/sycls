package com.b1.ticket;

import com.b1.content.entity.Round;
import com.b1.place.RoundHelper;
import com.b1.seat.SeatGradeHelper;
import com.b1.seat.SeatReservationLogHelper;
import com.b1.seat.entity.SeatGrade;
import com.b1.seat.entity.SeatReservationLog;
import com.b1.ticket.dto.TicketAddRequestDto;
import com.b1.ticket.dto.TicketAddResponseDto;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Category Service")
@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final RoundHelper roundHelper;
    private final SeatGradeHelper seatGradeHelper;
    private final SeatReservationLogHelper seatReservationLogHelper;

    /**
     * 티켓 등록
     *
     * @return
     */
    public TicketAddResponseDto addTicket(final TicketAddRequestDto requestDto) {
        Set<Long> seatGradeIds = requestDto.seatGradeIds();
        final Round selectedRound = roundHelper.getRound(requestDto.roundId());

        final Set<SeatGrade> seatGradesForSelectedRound = seatGradeHelper
                .getAllSeatGradeByContentAndSeatGradeIds(selectedRound, seatGradeIds);

        seatReservationLogHelper.getAllSeatReservationLogs(seatGradesForSelectedRound);

        final Set<SeatReservationLog> newSeatReservationLogs = new LinkedHashSet<>();
        for (SeatGrade seatGrade : seatGradesForSelectedRound) {
            newSeatReservationLogs.add(SeatReservationLog.addSeatReservationLog(seatGrade));
        }
        seatReservationLogHelper.addAllSeatReservationLogs(newSeatReservationLogs);
        return TicketAddResponseDto.of(selectedRound.getId(), seatGradesForSelectedRound);
    }


}
