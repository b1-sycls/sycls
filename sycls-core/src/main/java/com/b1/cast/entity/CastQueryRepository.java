package com.b1.cast.entity;

import com.b1.cast.entity.dto.CastGetAdminResponseDto;
import com.b1.cast.entity.dto.CastGetUserResponseDto;
import com.b1.round.entity.QRound;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CastQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 출연진 조회 (어드민)
     */
    public List<CastGetAdminResponseDto> getAllCastsByRoundIdForAdmin(final Long roundId) {
        QRound round = QRound.round;
        QCast cast = QCast.cast;

        return jpaQueryFactory
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

    /**
     * 출연진 조회 (유저)
     */
    public List<CastGetUserResponseDto> getAllCastsByRoundIdForUser(final Long roundId) {
        QRound round = QRound.round;
        QCast cast = QCast.cast;

        return jpaQueryFactory
                .select(Projections.constructor(
                        CastGetUserResponseDto.class,
                        cast.id,
                        cast.name,
                        cast.imagePath,
                        cast.status
                ))
                .from(cast)
                .leftJoin(cast.round, round)
                .where(cast.round.id.eq(roundId)
                        .and(cast.status.eq(CastStatus.SCHEDULED)))
                .fetch();
    }
}
