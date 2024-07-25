package com.b1.review;

import static com.b1.content.entity.QContent.content;
import static com.b1.review.entity.QReview.review;
import static com.b1.user.entity.QUser.user;

import com.b1.review.dto.ReviewGetResponseDto;
import com.b1.review.entity.ReviewStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<ReviewGetResponseDto> getAllReviews(
            final Long contentId,
            final Pageable pageable
    ) {

        List<ReviewGetResponseDto> reviewList = jpaQueryFactory
                .select(Projections.constructor
                        (
                                ReviewGetResponseDto.class,
                                user.email,
                                review.comment,
                                review.rating,
                                review.createdAt,
                                review.updatedAt
                        )
                )
                .from(review)
                .leftJoin(user).on(review.user.id.eq(user.id))
                .leftJoin(content).on(review.content.id.eq(content.id))
                .where(
                        review.status.eq(ReviewStatus.ENABLE),
                        content.id.eq(contentId)
                )
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(review.count())
                .from(review)
                .leftJoin(user).on(review.user.id.eq(user.id))
                .leftJoin(content).on(review.content.id.eq(content.id))
                .where(
                        review.status.eq(ReviewStatus.ENABLE),
                        content.id.eq(contentId)
                );
        return PageableExecutionUtils.getPage(reviewList, pageable, total::fetchOne);
    }

    /**
     * 관리자 공연 전체 조회
     */
    public Page<ReviewGetResponseDto> getAllReviewsByContent(
            final Long contentId,
            final String email,
            final String nickName,
            final ReviewStatus reviewStatus,
            final Pageable pageable
    ) {

        List<ReviewGetResponseDto> reviewList = jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReviewGetResponseDto.class,
                                user.nickname,
                                review.comment,
                                review.rating,
                                review.createdAt,
                                review.updatedAt
                        )
                )
                .from(review)
                .leftJoin(user).on(review.user.id.eq(user.id))
                .leftJoin(content).on(review.content.id.eq(content.id))
                .where(
                        review.content.id.eq(contentId),
                        emailLike(email),
                        nickNameLike(nickName),
                        reviewStatusEq(reviewStatus)
                )
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(
                        review.content.id.eq(contentId),
                        emailLike(email),
                        nickNameLike(nickName),
                        reviewStatusEq(reviewStatus)
                );

        return PageableExecutionUtils.getPage(reviewList, pageable, total::fetchOne);
    }

    /**
     * 관리자 리뷰 전체 조회시 검색조건 email
     */
    private BooleanExpression emailLike(final String email) {
        return StringUtils.hasText(email) ? user.email.like(email) : null;
    }

    /**
     * 관리자 리뷰 전체 조회시 검색조건 nickName
     */
    private BooleanExpression nickNameLike(final String nickName) {
        return StringUtils.hasText(nickName) ? user.email.like(nickName) : null;
    }

    /**
     * 관리자 리뷰 전체 조회시 검색조건 reviewStatus
     */
    private BooleanExpression reviewStatusEq(final ReviewStatus status) {
        return status != null ? review.status.eq(status) : null;
    }


    /**
     * 리뷰 상세조회
     */
    public ReviewGetResponseDto getReview(final Long reviewId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ReviewGetResponseDto.class,
                                user.nickname,
                                review.comment,
                                review.rating,
                                review.createdAt,
                                review.updatedAt
                        )
                )
                .from(review)
                .leftJoin(user).on(review.user.id.eq(user.id))
                .where(
                        review.id.eq(reviewId)
                )
                .fetchOne();
    }
}
