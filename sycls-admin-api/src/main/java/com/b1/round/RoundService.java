package com.b1.round;

import com.b1.content.ContentHelper;
import com.b1.content.entity.Content;
import com.b1.exception.customexception.InvalidDateException;
import com.b1.exception.customexception.InvalidTimeException;
import com.b1.exception.customexception.RoundConflictingReservationException;
import com.b1.exception.customexception.RoundStatusEqualsException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.place.PlaceHelper;
import com.b1.place.entity.Place;
import com.b1.round.dto.RoundAddRequestDto;
import com.b1.round.dto.RoundUpdateRequestDto;
import com.b1.round.dto.RoundUpdateStatusRequestDto;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Round Service")
@Service
@RequiredArgsConstructor
@Transactional
public class RoundService {

    private final RoundHelper roundHelper;
    private final ContentHelper contentHelper;
    private final PlaceHelper placeHelper;

    public void addRound(RoundAddRequestDto requestDto) {

        final LocalDate dtoStartDate = requestDto.startDate();
        final LocalTime dtoStartTime = requestDto.startTime();
        final LocalTime dtoEndTime = requestDto.endTime();

        checkReservationTime(dtoStartDate, dtoStartTime, dtoEndTime);

        Place place = placeHelper.getPlace(requestDto.placeId());

        List<Round> roundList = roundHelper.getAllRoundsByPlaceId(place.getId(), dtoStartDate);

        checkRoundConflictingReservation(roundList, dtoStartTime, dtoEndTime);

        Content content = contentHelper.getContent(requestDto.contentId());

        Round round = Round.addRound(
                requestDto.sequence(),
                dtoStartDate,
                dtoStartTime,
                dtoEndTime,
                requestDto.status(),
                content,
                place
        );

        roundHelper.saveRound(round);
    }

    public void updateRoundStatus(Long roundId, RoundUpdateStatusRequestDto requestDto) {

        Round round = roundHelper.findById(roundId);

        if (round.getStatus() == requestDto.status()) {
            log.error("회차 스테이터스 동일 오류 | roundId : {}", roundId);
            throw new RoundStatusEqualsException(RoundErrorCode.ROUND_STATUS_EQUALS);
        }

        round.updateStatus(requestDto.status());
    }

    public void updateRound(Long roundId, RoundUpdateRequestDto requestDto) {

        final LocalDate dtoStartDate = requestDto.startDate();
        final LocalTime dtoStartTime = requestDto.startTime();
        final LocalTime dtoEndTime = requestDto.endTime();

        checkReservationTime(dtoStartDate, dtoStartTime, dtoEndTime);

        Round round = roundHelper.findById(roundId);

        RoundStatus.checkClosed(round.getStatus());

        final Long placeId = round.getPlace().getId();

        List<Round> roundList = roundHelper.getAllRoundsByPlaceId(placeId, dtoStartDate);

        roundList.removeIf(roundInList -> roundInList.getId().equals(round.getId()));

        checkRoundConflictingReservation(roundList, dtoStartTime, dtoEndTime);

        round.updateDateAndTime(dtoStartDate, dtoStartTime, dtoEndTime);
    }

    private void checkReservationTime(LocalDate startDate, LocalTime startTime,
            LocalTime endTime) {

        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today)) {
            log.error("공연 날짜가 과거일 수 없음 | date : {}", startDate);
            throw new InvalidDateException(RoundErrorCode.INVALID_DATE);
        }

        if (endTime.isBefore(startTime)) {
            log.error("시작시간이 종료시간보다 늦을 수 없음");
            throw new InvalidTimeException(RoundErrorCode.INVALID_TIME);
        }
    }

    private void checkRoundConflictingReservation(List<Round> roundList,
            LocalTime dtoStartTime, LocalTime dtoEndTime) {
        for (Round roundInList : roundList) {

            final LocalTime savedStartTime = roundInList.getStartTime();
            final LocalTime savedEndTime = roundInList.getEndTime();

            // 바꿀 시간이 공연시간안에 걸리는 경우
            if ((dtoStartTime.isBefore(savedEndTime) && dtoEndTime.isAfter(savedStartTime))
                    // 바꿀 공연 시작시간이 기존의 시작시간이나 종료시간과 겹치는 경우
                    || (dtoStartTime.equals(savedStartTime) || dtoStartTime.equals(savedEndTime))
                    // 바꿀 공연 종료시간이 기존의 시작시간이나 종료시간과 겹치는 경우
                    || (dtoEndTime.equals(savedEndTime) || dtoEndTime.equals(savedStartTime))
            ) {
                log.error("예약하려는 시간이 이미 예약이 되어있어 실패");
                throw new RoundConflictingReservationException(
                        RoundErrorCode.CONFLICTING_RESERVATION);
            }
        }
    }
}
