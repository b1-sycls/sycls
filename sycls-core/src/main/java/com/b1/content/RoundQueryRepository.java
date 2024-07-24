package com.b1.content;

import com.b1.content.entity.QRound;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
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

        Integer fetchCount = queryFactory.selectOne()
                .from(round)
                .where(round.place.id.eq(placeId)
                        .and(round.startDate.eq(startDate))
                        .and(round.startTime.lt(endTime).and(round.endTime.gt(startTime)))
                ).fetchFirst();

        return fetchCount != null;
    }
}
