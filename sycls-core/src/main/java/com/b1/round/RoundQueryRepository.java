package com.b1.round;

import com.b1.category.entity.QCategory;
import com.b1.content.entity.QContent;
import com.b1.place.entity.QPlace;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import com.b1.round.dto.RoundSimpleResponseDto;
import com.b1.round.entity.QRound;
import com.b1.round.entity.Round;
import com.b1.round.entity.RoundStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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

    public List<RoundInfoGetAdminResponseDto> getAllRoundsInfoByContentIdForAdmin(Long contentId) {
        QRound round = QRound.round;
        QContent content = QContent.content;

        return queryFactory
                .select(Projections.constructor(
                        RoundInfoGetAdminResponseDto.class,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status
                ))
                .from(round)
                .leftJoin(round.content, content)
                .where(content.id.eq(contentId))
                .orderBy(round.sequence.asc())
                .fetch();
    }

    public List<RoundInfoGetUserResponseDto> getAllRoundsInfoByContentIdForUser(Long contentId) {
        QRound round = QRound.round;
        QContent content = QContent.content;

        return queryFactory
                .select(Projections.constructor(
                        RoundInfoGetUserResponseDto.class,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime
                ))
                .from(round)
                .leftJoin(round.content, content)
                .where(content.id.eq(contentId))
                .orderBy(round.sequence.asc())
                .fetch();
    }

    public RoundDetailInfoAdminResponseDto getRoundByRoundIdForAdmin(Long roundId) {
        QRound round = QRound.round;
        QContent content = QContent.content;
        QCategory category = QCategory.category;
        QPlace place = QPlace.place;

        return queryFactory
                .select(Projections.constructor(
                        RoundDetailInfoAdminResponseDto.class,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status,
                        content.id,
                        content.title,
                        content.description,
                        content.mainImagePath,
                        content.status,
                        category.id,
                        category.name,
                        category.status,
                        place.id,
                        place.name,
                        place.location,
                        place.status
                ))
                .from(round)
                .leftJoin(round.content, content)
                .leftJoin(round.place, place)
                .leftJoin(content.category, category)
                .where(round.id.eq(roundId))
                .fetchOne();
    }

    public Page<RoundSimpleResponseDto> getAllSimpleRoundsForAdmin(Long contentId,
            RoundStatus status, Pageable pageable) {
        QRound round = QRound.round;
        QContent content = QContent.content;

        List<RoundSimpleResponseDto> roundList = queryFactory
                .select(Projections.constructor(
                        RoundSimpleResponseDto.class,
                        round.id,
                        content.title,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status
                ))
                .from(round)
                .leftJoin(round.content, content)
                .where(
                        contentIdEq(contentId),
                        statusEq(status)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(round.sequence.asc())
                .fetch();

        JPAQuery<Long> total = queryFactory
                .select(round.count())
                .from(round)
                .where(
                        contentIdEq(contentId),
                        statusEq(status)
                );

        return PageableExecutionUtils.getPage(roundList, pageable, total::fetchOne);
    }

    private BooleanExpression contentIdEq(Long contentId) {
        return contentId == null ? null : QRound.round.content.id.eq(contentId);
    }

    private BooleanExpression statusEq(RoundStatus status) {
        return status == null ? null : QRound.round.status.eq(status);
    }
}
