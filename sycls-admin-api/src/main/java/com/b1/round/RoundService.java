package com.b1.round;

import com.b1.common.PageResponseDto;
import com.b1.content.ContentHelper;
import com.b1.content.entity.Content;
import com.b1.exception.customexception.InvalidDateException;
import com.b1.exception.customexception.InvalidTimeException;
import com.b1.exception.customexception.RoundConflictingReservationException;
import com.b1.exception.errorcode.RoundErrorCode;
import com.b1.place.PlaceHelper;
import com.b1.place.entity.Place;
import com.b1.round.dto.RoundAddRequestDto;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundDetailResponseDto;
import com.b1.round.dto.RoundSearchCondRequest;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.dto.RoundUpdateRequestDto;
import com.b1.round.dto.RoundUpdateStatusRequestDto;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.b1.s3.S3Util;
import com.b1.util.PageUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    /**
     * 회차 등록
     */
    public void addRound(final RoundAddRequestDto requestDto) {

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
                RoundStatus.WAITING,
                content,
                place
        );

        roundHelper.saveRound(round);
    }

    /**
     * 회차 상태 수정
     */
    public void updateRoundStatus(final Long roundId,
            final RoundUpdateStatusRequestDto requestDto) {

        Round round = roundHelper.findById(roundId);

        RoundStatus.checkEqualsStatus(round.getStatus(), requestDto.status());

        round.updateStatus(requestDto.status());
    }

    /**
     * 회차 정보 수정
     */
    public void updateRound(final Long roundId, final RoundUpdateRequestDto requestDto) {

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

    /**
     * 회차 단일 조회
     */
    @Transactional(readOnly = true)
    public RoundDetailResponseDto getRound(final Long roundId) {

        RoundDetailInfoAdminResponseDto responseDto = roundHelper.getRoundDetail(roundId);

        if (responseDto == null) {
            return null;
        }

        responseDto.updateMainImagePath(
                S3Util.makeResponseImageDir(responseDto.getMainImagePath()));

        return RoundDetailResponseDto.of(responseDto);
    }

    /**
     * 회차 상세조회
     */
    @Transactional(readOnly = true)
    public PageResponseDto<RoundSimpleAdminResponseDto> getAllRounds(
            final RoundSearchCondRequest request) {

        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortDirection()),
                request.getSortProperty());

        PageUtil.checkPageNumber(request.getPage());

        Pageable pageable = PageRequest.of(request.getPage() - 1, 10, sort);

        Page<RoundSimpleAdminResponseDto> pageResponseDto = roundHelper.getAllSimpleRoundsForAdmin(
                request, pageable);

        return PageResponseDto.of(pageResponseDto);
    }

    /**
     * 예약 시간 유효성 검증
     */
    private void checkReservationTime(final LocalDate startDate, final LocalTime startTime,
            final LocalTime endTime) {

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

    /**
     * 기존예약과 충돌 확인
     */
    private void checkRoundConflictingReservation(final List<Round> roundList,
            final LocalTime dtoStartTime, final LocalTime dtoEndTime) {
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
