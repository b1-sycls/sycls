package com.b1.seatgrade;

import com.b1.seatgrade.dto.SeatGradeGetAllResponseDto;
import com.b1.seatgrade.dto.SeatGradeGetResponseDto;
import com.b1.seatgrade.dto.SeatGradeUserGetDto;
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
    @Transactional(readOnly = true)
    public SeatGradeGetAllResponseDto getAllSeatGradesUser(final Long roundId) {
        List<SeatGradeUserGetDto> seatGradeList = seatGradeHelper.getAllSeatGradesUser(roundId);
        List<SeatGradeGetResponseDto> responseDto = SeatGradeGetResponseDto.of(seatGradeList);
        return SeatGradeGetAllResponseDto.of(roundId, responseDto);
    }
}
