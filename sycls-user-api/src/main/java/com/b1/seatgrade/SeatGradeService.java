package com.b1.seatgrade;

import com.b1.seatgrade.dto.SeatGradeGetAllResponseDto;
import com.b1.seatgrade.dto.SeatGradeUserGetResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatGradeService {

    private final SeatGradeHelper seatGradeHelper;

    /**
     * 해당 회차의 좌석-등급 전체 조회
     */
    public SeatGradeGetAllResponseDto getAllSeatGradesUser(final Long roundId) {
        List<SeatGradeUserGetResponseDto> seatGradeList = seatGradeHelper
                .getAllSeatGradesUser(roundId);
        return SeatGradeGetAllResponseDto.of(roundId, seatGradeList);
    }
}
