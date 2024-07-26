package com.b1.seatgrade;

import com.b1.round.entity.QRound;
import com.b1.seat.entity.QSeat;
import com.b1.seatgrade.dto.SeatGradeGetResponseDto;
import com.b1.seatgrade.entity.QSeatGrade;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "Seat Grade Query Repository")
@Repository
@RequiredArgsConstructor
public class SeatGradeQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * seatGrade 전체 조회
     */
    public List<SeatGradeGetResponseDto> getAllSeatGrades(
            final Long roundId
    ) {
        QSeat seat = QSeat.seat;
        QSeatGrade seatGrade = QSeatGrade.seatGrade;

        return jpaQueryFactory.select(
                        Projections.constructor(
                                SeatGradeGetResponseDto.class,
                                seat.id,
                                seat.code,
                                seatGrade.id,
                                seatGrade.grade,
                                seatGrade.price,
                                seatGrade.status
                        )
                )
                .from(seatGrade)
                .leftJoin(seat).on(seatGrade.seat.id.eq(seat.id))
                .where(seatGrade.round.id.eq(roundId))
                .fetch();
    }

    /**
     * 등록된 좌석-등급의 총 갯수 조회
     */
    public Integer getTotalCount(Long roundId) {
        QSeatGrade seatGrade = QSeatGrade.seatGrade;
        QRound round = QRound.round;

        int intExact = Math.toIntExact(jpaQueryFactory
                .select(seatGrade.count())
                .from(seatGrade)
                .leftJoin(round).on(seatGrade.round.id.eq(round.id))
                .where(round.id.eq(roundId))
                .fetchOne());
        return intExact;
    }

}
