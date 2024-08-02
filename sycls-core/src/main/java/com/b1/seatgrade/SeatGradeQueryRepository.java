package com.b1.seatgrade;

import com.b1.place.entity.PlaceStatus;
import com.b1.place.entity.QPlace;
import com.b1.round.dto.RoundSeatGradeStatusDto;
import com.b1.round.entity.QRound;
import com.b1.seat.entity.QSeat;
import com.b1.seatgrade.dto.SeatGradeAdminGetResponseDto;
import com.b1.seatgrade.dto.SeatGradeUserGetDto;
import com.b1.seatgrade.entity.QSeatGrade;
import com.b1.seatgrade.entity.SeatGradeStatus;
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
                .where(
                        seatGrade.round.id.eq(roundId),
                        seatGrade.status.eq(SeatGradeStatus.ENABLE)
                )
                .fetch();
    }

    /**
     * 사용자 - 해당 회차의 좌석-등급 조회
     */
    public List<SeatGradeUserGetDto> getAllSeatGradesUser(final Long roundId) {
        QSeatGrade seatGrade = QSeatGrade.seatGrade;
        QSeat seat = QSeat.seat;

        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                SeatGradeUserGetDto.class,
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
                .where(
                        seatGrade.round.id.eq(roundId),
                        seatGrade.status.eq(SeatGradeStatus.DISABLE).not()
                )
                .fetch();
    }

    /**
     * 해당 회차의 공연장의 최대좌석수와 enable 된 seatGrade 의 수를 반환
     * TODO SeatGradeQueryRepository 의 getTotalCount 와 PlaceQueryRepository 의 getMaxSeatFromPlace 와 겹치는 쿼리
     */
    public RoundSeatGradeStatusDto getPlaceMaxSeatAndEnableSeatGradeByRoundId(
            final Long roundId
    ) {
        QRound round = QRound.round;
        QPlace place = QPlace.place;
        QSeatGrade seatGrade = QSeatGrade.seatGrade;

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoundSeatGradeStatusDto.class,
                        place.maxSeat,
                        seatGrade.count()
                ))
                .from(seatGrade)
                .leftJoin(seatGrade.round, round)
                .leftJoin(round.place, place)
                .where(round.id.eq(roundId)
                        .and(seatGrade.status.eq(SeatGradeStatus.ENABLE)
                                .and(place.status.eq(PlaceStatus.ENABLE))))
                .fetchOne();
    }
}
