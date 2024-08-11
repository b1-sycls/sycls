package com.b1.seatgrade;

import com.b1.place.PlaceHelper;
import com.b1.place.entity.PlaceStatus;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.b1.seat.SeatHelper;
import com.b1.seat.entity.Seat;
import com.b1.seatgrade.dto.SeatGradeAddRequestDto;
import com.b1.seatgrade.dto.SeatGradeAdminGetResponseDto;
import com.b1.seatgrade.dto.SeatGradeGetAllResponseDto;
import com.b1.seatgrade.dto.SeatGradeUpdateRequestDto;
import com.b1.seatgrade.entity.SeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "Seat Grade Service")
@Service
@RequiredArgsConstructor
@Transactional
public class SeatGradeService {

    private final SeatGradeHelper seatGradeHelper;
    private final RoundHelper roundHelper;
    private final SeatHelper seatHelper;
    private final PlaceHelper placeHelper;

    /**
     * 좌석 등급 등록
     */
    public void addSeatGrades(final SeatGradeAddRequestDto requestDto) {
        // 좌석-등급 등록을 위한 예외 처리
        Round round = roundHelper.findById(requestDto.roundId());
        PlaceStatus.checkDeleted(round.getPlace().getStatus());

        // 중복되는 좌석에 대한 등록이 있는지 확인
        seatGradeHelper.checkAllSeatGradesByRoundIdAndSeatIdIn(
                round.getId(),
                requestDto.seatId()
        );

        // 좌석-등급 등록
        Seat seat = seatGradeHelper.getSeatForAddSeatGrade(requestDto.seatId());
        SeatGrade seatGrade = SeatGrade.addSeatGrade(
                requestDto.seatGradeType(),
                requestDto.price(),
                seat,
                round
        );

        seatGradeHelper.saveSeatGrades(seatGrade);
    }

    /**
     * 전체 좌석에 대한 등급 설정 완료 확인
     */
    @Transactional(readOnly = true)
    public Boolean confirmAllSeatSetting(final Long roundId) {
        Round round = roundHelper.findById(roundId);
        // 회차의 공연장 총 좌석수와 등록된 SeatGrade 를 비교
        return seatGradeHelper.checkMaxSeatsAndSeatCount(
                round.getPlace().getId(),
                round.getPlace().getMaxSeat()
        );
    }

    /**
     * 좌석-등급 전체 조회
     */
    @Transactional(readOnly = true)
    public SeatGradeGetAllResponseDto getAllSeatGrades(final Long roundId) {
        List<SeatGradeAdminGetResponseDto> seatGradeList = seatGradeHelper.getAllSeatGrades(
                roundId);
        return SeatGradeGetAllResponseDto.of(roundId, seatGradeList);
    }

    /**
     * 좌석-등급 수정
     */
    public void updateSeatGrades(final SeatGradeUpdateRequestDto requestDto) {
        Round round = roundHelper.findById(requestDto.roundId());
        RoundStatus.checkAvailable(round.getStatus());
        SeatGrade seatGrade = seatGradeHelper.findById(requestDto.seatGradeId());
        seatGrade.updateSeatGrade(requestDto.seatGradeType(), requestDto.price());
    }

    /**
     * 좌석-등급 삭제
     */
    public void deleteSeatGrades(final Long roundId, final Long seatGradeId) {
        Round round = roundHelper.findById(roundId);
        RoundStatus.checkAvailable(round.getStatus());
        SeatGrade seatGrade = seatGradeHelper.findById(seatGradeId);
        SeatGradeStatus.checkDeleted(seatGrade.getStatus());
        seatGrade.deleteSeatGrade();
    }
}
