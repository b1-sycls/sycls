package com.b1.content;

import com.b1.category.entity.QCategory;
import com.b1.content.dto.ContentDetailImagePathGetAdminResponseDto;
import com.b1.content.dto.ContentDetailImagePathGetUserResponseDto;
import com.b1.content.dto.ContentGetAdminResponseDto;
import com.b1.content.dto.ContentGetUserResponseDto;
import com.b1.content.entity.ContentDetailImage;
import com.b1.content.entity.ContentStatus;
import com.b1.content.entity.QContent;
import com.b1.content.entity.QContentDetailImage;
import com.b1.round.entity.QRound;
import com.b1.round.entity.RoundStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Slf4j(topic = "Content Query Repository")
@Repository
@RequiredArgsConstructor
public class ContentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 서브이미지 리스트 반환
     */
    public List<ContentDetailImage> getByContentDetailImagesByContentId(final Long contentId) {

        QContent content = QContent.content;
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;

        return jpaQueryFactory
                .selectFrom(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId))
                .fetch();
    }

    /**
     * (어드민) 단일 조회시 필요한 공연의 서브이미지 정보 조회
     */
    public List<ContentDetailImagePathGetAdminResponseDto> getAllContentDetailImagesPathByContentIdForAdmin(
            final Long contentId) {

        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;
        QContent content = QContent.content;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ContentDetailImagePathGetAdminResponseDto.class,
                        contentDetailImage.id,
                        contentDetailImage.detailImagePath,
                        contentDetailImage.status
                ))
                .from(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId))
                .orderBy(contentDetailImage.id.asc())
                .fetch();
    }

    /**
     * (유저) 단일 조회시 필요한 공연의 서브이미지 정보 조회
     */
    public List<ContentDetailImagePathGetUserResponseDto> getAllContentDetailImagesPathByContentIdForUser(
            final Long contentId) {

        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;
        QContent content = QContent.content;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ContentDetailImagePathGetUserResponseDto.class,
                        contentDetailImage.id,
                        contentDetailImage.detailImagePath,
                        contentDetailImage.status
                ))
                .from(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId)
                        .and(content.status.eq(ContentStatus.VISIBLE)))
                .orderBy(contentDetailImage.id.asc())
                .fetch();
    }

    /**
     * (어드민) 단일 조회시 필요한 공연의 정보 조회
     */
    public ContentGetAdminResponseDto getByContentByContentIdForAdmin(final Long contentId) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ContentGetAdminResponseDto.class,
                        content.id,
                        content.title,
                        content.description,
                        content.mainImagePath,
                        content.status,
                        category.name
                ))
                .from(content)
                .leftJoin(content.category, category)
                .where(content.id.eq(contentId))
                .fetchOne();
    }

    /**
     * (유저) 단일 조회시 필요한 공연의 정보 조회
     */
    public ContentGetUserResponseDto getByContentByContentIdForUser(final Long contentId) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ContentGetUserResponseDto.class,
                        content.id,
                        content.title,
                        content.description,
                        content.mainImagePath,
                        content.status,
                        category.name
                ))
                .from(content)
                .leftJoin(content.category, category)
                .where(content.id.eq(contentId)
                        .and(content.status.eq(ContentStatus.VISIBLE)))
                .fetchOne();
    }

    /**
     * (어드민) 단일 조회시 필요한 공연의 회차 정보 페이징
     */
    public Page<ContentGetAdminResponseDto> getAllContentForAdmin(final Long categoryId,
            final String titleKeyword, final ContentStatus status, final Pageable pageable) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        List<ContentGetAdminResponseDto> contentList = jpaQueryFactory
                .select(Projections.constructor(
                        ContentGetAdminResponseDto.class,
                        content.id,
                        content.title,
                        content.description,
                        content.mainImagePath,
                        content.status,
                        category.name
                ))
                .from(content)
                .leftJoin(content.category, category)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(status)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(content.createdAt.desc())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(content.count())
                .from(content)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(status)
                );

        return PageableExecutionUtils.getPage(contentList, pageable, total::fetchOne);
    }

    /**
     * (유저) 단일 조회시 필요한 공연의 회차 정보 페이징
     */
    public Page<ContentGetUserResponseDto> getAllContentForUser(final Long categoryId,
            final String titleKeyword, final Pageable pageable) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        List<ContentGetUserResponseDto> contentList = jpaQueryFactory
                .select(Projections.constructor(
                        ContentGetUserResponseDto.class,
                        content.id,
                        content.title,
                        content.description,
                        content.mainImagePath,
                        content.status,
                        category.name
                ))
                .from(content)
                .leftJoin(content.category, category)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(ContentStatus.VISIBLE)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(content.createdAt.desc())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(content.count())
                .from(content)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(ContentStatus.VISIBLE)
                );

        return PageableExecutionUtils.getPage(contentList, pageable, total::fetchOne);
    }

    /**
     * 공연이 활성화 상태로 변환 가능인지 회차 확인
     */
    public Long checkRoundStatusByContentId(final Long contentId) {

        QContent content = QContent.content;
        QRound round = QRound.round;

        return jpaQueryFactory
                .select(round.count())
                .from(round)
                .leftJoin(round.content, content)
                .where(content.id.eq(contentId)
                        .and(round.status.eq(RoundStatus.CLOSED).not()))
                .fetchOne();
    }

    /**
     * 카테고리 id 별 조건 설정
     */
    private BooleanExpression categoryEq(final Long categoryId) {
        return categoryId == null ? null : QCategory.category.id.eq(categoryId);
    }

    /**
     * 단어별 검색 조건 설정
     */
    private BooleanExpression titleContains(final String titleKeyword) {
        return StringUtils.hasText(titleKeyword) ? QContent.content.title.containsIgnoreCase(
                titleKeyword) : null;
    }

    /**
     * 상태별 검색 조건 설정
     */
    private BooleanExpression statusEq(final ContentStatus status) {
        return status == null ? null : QContent.content.status.eq(status);
    }
}
