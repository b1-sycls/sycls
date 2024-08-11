package com.b1.payment;

import com.b1.config.TossConfig;
import com.b1.payment.dto.ClientResponseDto;
import com.b1.payment.dto.PaymentSuccessRequestDto;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import com.b1.reservation.ReservationHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.ticket.TicketHelper;
import com.b1.ticket.entity.Ticket;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j(topic = "Toss Payment Service")
@Service
@RequiredArgsConstructor
@Transactional
public class TossPaymentService {

    private final TossConfig tossConfig;
    private final ReservationHelper reservationHelper;
    private final TicketHelper ticketHelper;
    private final TossPaymentHelper tossPaymentHelper;

    /**
     * 토스페이먼츠 ClientKey 및 User정보 전송
     */
    public ClientResponseDto getClientKey(
            final User user
    ) {
        return ClientResponseDto.of(tossConfig.getPaymentClientKey(), user);
    }

    /**
     * 토스페이먼츠 결제 시도
     */
    public TossPaymentRestResponse confirm(
            final TossConfirmRequestDto requestDto,
            final User user
    ) {

        Set<Long> reservationByUser = reservationHelper
                .getReservationByUser(requestDto.roundId(), user.getId());

        return tossPaymentHelper
                .confirmPayment(tossConfig, requestDto, reservationByUser, user.getId());
    }

    /**
     * 티켓 발행
     */
    public void successReservation(
            final PaymentSuccessRequestDto requestDto,
            final User user
    ) {
        List<SeatGrade> reservationByUserWithPayment = reservationHelper
                .getReservationByUserWithPayment(requestDto.roundId(), user.getId());

        Ticket ticket = Ticket.addTicket(
                requestDto.orderId(),
                requestDto.price(),
                user,
                reservationByUserWithPayment.get(0).getRound());

        Ticket saveTicket = ticketHelper.addTicket(ticket);

        reservationByUserWithPayment.forEach(sg -> {
            sg.updateTicket(saveTicket.getId());
            sg.soldOutSeatGrade();
        });
        reservationHelper.clearReservation(requestDto.roundId(), user.getId());
        tossPaymentHelper.clearReservation(requestDto.roundId(), user.getId());
    }

}
