package com.b1.seatgrade;

import com.b1.round.entity.QRound;
import com.b1.seat.entity.QSeat;
import com.b1.seatgrade.dto.SeatGradeAdminGetResponseDto;
import com.b1.seatgrade.dto.SeatGradeUserGetResponseDto;
import com.b1.seatgrade.entity.QSeatGrade;
import com.b1.seatgrade.entity.SeatGrade;
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
    public List<SeatGradeAdminGetResponseDto> getAllSeatGrades(
            final Long roundId
    ) {
        QSeat seat = QSeat.seat;
        QSeatGrade seatGrade = QSeatGrade.seatGrade;

        return jpaQueryFactory.select(
                        Projections.constructor(
                                SeatGradeAdminGetResponseDto.class,
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

        List<SeatGrade> count = jpaQueryFactory
                .selectFrom(seatGrade)
                .leftJoin(round).on(seatGrade.round.id.eq(round.id))
                .where(round.id.eq(roundId))
                .fetch();

        return count.size();
    }

    /**
     * 사용자 - 해당 회차의 좌석-등급 조회
     */
    public List<SeatGradeUserGetResponseDto> getAllSeatGradesUser(Long roundId) {
        QSeatGrade seatGrade = QSeatGrade.seatGrade;
        QSeat seat = QSeat.seat;

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                SeatGradeUserGetResponseDto.class,
                                seat.id,
                                seat.code,
                                seatGrade.id,
                                seatGrade.grade,
                                seatGrade.price
                        )
                )
                .from(seatGrade)
                .leftJoin(seat).on(seatGrade.seat.id.eq(seat.id))
                .where(seatGrade.round.id.eq(roundId))
                .fetch();
    }
}
