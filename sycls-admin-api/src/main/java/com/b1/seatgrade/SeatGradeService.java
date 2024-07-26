package com.b1.seatgrade;

import com.b1.place.entity.PlaceStatus;
import com.b1.round.RoundHelper;
import com.b1.round.entity.Round;
import com.b1.seat.SeatHelper;
import com.b1.seat.entity.Seat;
import com.b1.seatgrade.dto.SeatGradeAddRequestDto;
import com.b1.seatgrade.entity.SeatGrade;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatGradeService {

    private final SeatGradeHelper seatGradeHelper;
    private final RoundHelper roundHelper;
    private final SeatHelper seatHelper;

    /**
     * 좌석 등급 등록
     */
    public void addSeatGrades(final SeatGradeAddRequestDto requestDto) {
        // 좌석-등급 등록을 위한 예외 처리
        Round round = roundHelper.findByIdAndContentId(requestDto.roundId(),
                requestDto.contentId());
        PlaceStatus.checkDeleted(round.getPlace().getStatus());

        // 좌석-등급 등록
        List<Seat> seatList = requestDto.seatIdList().stream()
                .map(seatHelper::getSeat)
                .toList();

        List<SeatGrade> seatGradeList = seatList.stream()
                .map(seat -> SeatGrade.addSeatGrade(
                        requestDto.seatGradeType(),
                        requestDto.price(),
                        seat,
                        round
                )).toList();

        seatGradeHelper.saveSeatGrades(seatGradeList);
    }
}
