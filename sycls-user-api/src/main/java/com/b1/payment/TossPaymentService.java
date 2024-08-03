package com.b1.payment;

import com.b1.config.TossConfig;
import com.b1.exception.customexception.TossPaymentException;
import com.b1.exception.errorcode.PaymentErrorCode;
import com.b1.payment.dto.ClientResponseDto;
import com.b1.payment.dto.PaymentSuccessRequestDto;
import com.b1.payment.dto.TossConfirmRequestDto;
import com.b1.payment.dto.TossPaymentRestResponse;
import com.b1.seatgrade.SeatGradeReservationLogHelper;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeReservationLog;
import com.b1.security.UserDetailsImpl;
import com.b1.ticket.TicketHelper;
import com.b1.ticket.entity.Ticket;
import com.b1.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import static com.b1.constant.TossConstant.AUTHORIZATION;
import static com.b1.constant.TossConstant.BASIC;
import static com.b1.constant.TossConstant.TOSS_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j(topic = "Toss Payment Service")
@Service
@RequiredArgsConstructor
@Transactional
public class TossPaymentService {

    private final TossConfig tossConfig;
    private final SeatGradeReservationLogHelper seatGradeReservationLogHelper;
    private final TicketHelper ticketHelper;

    /**
     * 토스페이먼츠 ClientKey 및 User정보 전송
     */
    public ClientResponseDto getClientKey(
            final User user
    ) {
        return ClientResponseDto.of(tossConfig.getPaymentClientKey(), user);
    }

    public ResponseEntity<TossPaymentRestResponse> confirm(
            final TossConfirmRequestDto requestDto
    ) {

        String authorization = Base64.getEncoder().encodeToString((tossConfig.getPaymentSecretKey() + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, BASIC + authorization);

        try {
            URL url = new URL(TOSS_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(AUTHORIZATION, authorization);
            headers.setContentType(APPLICATION_JSON);
        } catch (Exception e) {
            throw new TossPaymentException(PaymentErrorCode.TOSS_PAYMENT_EXCEPTION);
        }

        HttpEntity<TossConfirmRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

        return new RestTemplate()
                .postForEntity(TOSS_URL, requestEntity, TossPaymentRestResponse.class);
    }

    /**
     * 티켓 발행
     */
    public void successReservation(
            final PaymentSuccessRequestDto requestDto,
            final UserDetailsImpl userDetails
    ) {
        List<SeatGradeReservationLog> seatReservationLogsById = seatGradeReservationLogHelper
                .getSeatReservationLogsById(requestDto.seatGradeIds());
        seatReservationLogsById.forEach(SeatGradeReservationLog::deleteReservationStatus);

        Ticket ticket = Ticket.addTicket(
                requestDto.orderId(),
                requestDto.price(),
                userDetails.getUser(),
                seatReservationLogsById.get(0).getSeatGrade().getRound());

        Ticket saveTicket = ticketHelper.addTicket(ticket);

        seatReservationLogsById.forEach(log -> {
            SeatGrade seatGrade = log.getSeatGrade();
            seatGrade.updateTicket(saveTicket.getId());
            seatGrade.soldOutSeatGrade();
        });
    }

}
