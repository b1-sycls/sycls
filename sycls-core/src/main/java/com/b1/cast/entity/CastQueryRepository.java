package com.b1.cast.entity;

import com.b1.cast.entity.dto.CastGetAdminResponseDto;
import com.b1.round.entity.QRound;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CastQueryRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 출연진 조회 (어드민)
     */
    public List<CastGetAdminResponseDto> getAllCastsByRoundIdForAdmin(Long roundId) {
        QRound round = QRound.round;
        QCast cast = QCast.cast;

        return queryFactory
                .select(Projections.constructor(
                        CastGetAdminResponseDto.class,
                        cast.id,
                        cast.name,
                        cast.imagePath,
                        cast.status
                ))
                .from(cast)
                .leftJoin(cast.round, round)
                .where(cast.round.id.eq(roundId))
                .fetch();
    }
}
