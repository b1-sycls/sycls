package com.b1.round;

import com.b1.place.entity.QPlace;
import com.b1.round.entity.QRound;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "Round Query Repository")
@Repository
@RequiredArgsConstructor
public class RoundQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existsConflictingRounds(Long placeId, LocalDate startDate, LocalTime startTime,
            LocalTime endTime) {
        QRound round = QRound.round;
        QPlace place = QPlace.place;

        Integer fetchCount = queryFactory.selectOne()
                .from(round)
                .leftJoin(round.place, place)
                .where(round.place.id.eq(placeId)
                        .and(round.status.in(RoundStatus.AVAILABLE, RoundStatus.WAITING))
                        .and(round.startDate.eq(startDate))
                        .and(round.startTime.lt(endTime).and(round.endTime.gt(startTime))
                                .or(round.startTime.loe(startTime)
                                        .and(round.endTime.goe(startTime)))
                                .or(round.startTime.loe(endTime)
                                        .and(round.endTime.goe(endTime)))

                        )
                ).fetchFirst();

        return fetchCount != null;
    }

    public List<Round> getAllRoundsByPlaceId(Long placeId,
            LocalDate startDate) {
        QRound round = QRound.round;
        QPlace place = QPlace.place;

        return queryFactory
                .selectFrom(round)
                .leftJoin(round.place, place)
                .where(round.place.id.eq(placeId)
                        .and(round.status.in(RoundStatus.AVAILABLE, RoundStatus.WAITING))
                        .and(round.startDate.eq(startDate))
                ).fetch();
    }
}
