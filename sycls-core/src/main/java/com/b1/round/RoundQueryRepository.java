package com.b1.round;

import com.b1.place.entity.QPlace;
import com.b1.round.entity.QRound;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "Round Query Repository")
@Repository
@RequiredArgsConstructor
public class RoundQueryRepository {

    private final JPAQueryFactory queryFactory;

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
