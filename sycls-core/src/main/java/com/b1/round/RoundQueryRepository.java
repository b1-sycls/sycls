package com.b1.round;

import com.b1.category.entity.QCategory;
import com.b1.content.entity.QContent;
import com.b1.place.entity.QPlace;
import com.b1.round.dto.ContentAndRoundGetResponseDto;
import com.b1.round.dto.RoundDetailInfoAdminResponseDto;
import com.b1.round.dto.RoundDetailInfoUserResponseDto;
import com.b1.round.dto.RoundInfoGetAdminResponseDto;
import com.b1.round.dto.RoundInfoGetUserResponseDto;
import com.b1.round.dto.RoundSimpleAdminResponseDto;
import com.b1.round.dto.RoundSimpleUserResponseDto;
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

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 회차 Entity List 반환
     */
    public List<Round> getAllRoundsByPlaceId(final Long placeId, final LocalDate startDate) {

        QRound round = QRound.round;
        QPlace place = QPlace.place;

        return jpaQueryFactory
                .selectFrom(round)
                .leftJoin(round.place, place)
                .where(round.place.id.eq(placeId)
                        .and(round.status.eq(RoundStatus.CLOSED).not())
                        .and(round.startDate.eq(startDate))
                ).fetch();
    }

    /**
     * (어드민) 공연 단일 조회 기능에 들어가는 회차 정보
     */
    public List<RoundInfoGetAdminResponseDto> getAllRoundsInfoByContentIdForAdmin(
            final Long contentId) {

        QRound round = QRound.round;
        QContent content = QContent.content;
        QPlace place = QPlace.place;

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoundInfoGetAdminResponseDto.class,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status,
                        place.id,
                        place.name,
                        place.location,
                        place.maxSeat
                ))
                .from(round)
                .leftJoin(round.content, content)
                .leftJoin(round.place, place)
                .where(content.id.eq(contentId))
                .orderBy(round.sequence.asc())
                .fetch();
    }

    /**
     * (유저) 공연 단일 조회 기능에 들어가는 회차 정보
     */
    public List<RoundInfoGetUserResponseDto> getAllRoundsInfoByContentIdForUser(
            final Long contentId) {

        QRound round = QRound.round;
        QContent content = QContent.content;
        QPlace place = QPlace.place;

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoundInfoGetUserResponseDto.class,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status,
                        place.id,
                        place.name,
                        place.location,
                        place.maxSeat
                ))
                .from(round)
                .leftJoin(round.content, content)
                .leftJoin(round.place, place)
                .where(content.id.eq(contentId))
                .orderBy(round.sequence.asc())
                .fetch();
    }

    /**
     * (어드민) 회차 단일 상세 조회
     */
    public RoundDetailInfoAdminResponseDto getRoundDetailInfoForAdmin(final Long roundId) {

        QRound round = QRound.round;
        QContent content = QContent.content;
        QCategory category = QCategory.category;
        QPlace place = QPlace.place;

        return jpaQueryFactory
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

    /**
     * (유저) 회차 단일 상세 조회
     */
    public RoundDetailInfoUserResponseDto getRoundDetailInfoForUser(final Long roundId) {

        QRound round = QRound.round;
        QContent content = QContent.content;
        QCategory category = QCategory.category;
        QPlace place = QPlace.place;

        return jpaQueryFactory
                .select(Projections.constructor(
                        RoundDetailInfoUserResponseDto.class,
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
                .where(round.id.eq(roundId)
                        .and(round.status.eq(RoundStatus.CLOSED).not()))
                .fetchOne();
    }

    /**
     * (어드민) 회차 목록 정보 반환
     */
    public Page<RoundSimpleAdminResponseDto> getAllSimpleRoundsForAdmin(final Long contentId,
            final RoundStatus status, final Pageable pageable) {

        QRound round = QRound.round;
        QContent content = QContent.content;

        List<RoundSimpleAdminResponseDto> roundList = jpaQueryFactory
                .select(Projections.constructor(
                        RoundSimpleAdminResponseDto.class,
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

        JPAQuery<Long> total = jpaQueryFactory
                .select(round.count())
                .from(round)
                .where(
                        contentIdEq(contentId),
                        statusEq(status)
                );

        return PageableExecutionUtils.getPage(roundList, pageable, total::fetchOne);
    }

    /**
     * (유저) 회차 단일 간단 조회
     */
    public ContentAndRoundGetResponseDto getRoundSimple(final Long roundId) {

        QRound round = QRound.round;
        QContent content = QContent.content;
        QPlace place = QPlace.place;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ContentAndRoundGetResponseDto.class,
                        content.id,
                        content.title,
                        content.description,
                        place.id,
                        place.name,
                        place.location,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime
                ))
                .from(round)
                .leftJoin(round.content, content)
                .leftJoin(round.place, place)
                .where(round.id.eq(roundId))
                .fetchOne();
    }

    /**
     * (유저) 회차 목록 정보 반환
     */
    public Page<RoundSimpleUserResponseDto> getAllSimpleRoundsForUser(final Long contentId,
            final Pageable pageable) {

        QRound round = QRound.round;
        QContent content = QContent.content;

        List<RoundSimpleUserResponseDto> roundList = jpaQueryFactory
                .select(Projections.constructor(
                        RoundSimpleUserResponseDto.class,
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
                        statusNotClosed()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(round.sequence.asc())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(round.count())
                .from(round)
                .where(
                        contentIdEq(contentId),
                        statusNotClosed()
                );

        return PageableExecutionUtils.getPage(roundList, pageable, total::fetchOne);
    }

    /**
     * 공연 고유번호 검색 조건 설정
     */
    private BooleanExpression contentIdEq(final Long contentId) {
        return contentId == null ? null : QRound.round.content.id.eq(contentId);
    }

    /**
     * 회차 상태 검색 조건 설정
     */
    private BooleanExpression statusEq(final RoundStatus status) {
        return status == null ? null : QRound.round.status.eq(status);
    }

    /**
     * 유저 상태 검증 조건 설정
     */
    private BooleanExpression statusNotClosed() {
        return QRound.round.status.eq(RoundStatus.CLOSED).not();
    }
}
