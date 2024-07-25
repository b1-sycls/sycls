package com.b1.review;

import com.b1.content.entity.QContent;
import com.b1.review.dto.ReviewGetAllResponseDto;
import com.b1.review.entity.QReview;
import com.b1.review.entity.ReviewStatus;
import com.b1.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<ReviewGetAllResponseDto> getAllReviews(final Long contentId) {
        QReview review = QReview.review;
        QUser user = QUser.user;
        QContent content = QContent.content;

        return jpaQueryFactory
                .select(Projections.constructor
                        (
                                ReviewGetAllResponseDto.class,
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
    }

}
