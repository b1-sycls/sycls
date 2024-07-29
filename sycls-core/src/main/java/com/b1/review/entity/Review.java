package com.b1.review.entity;

import com.b1.common.TimeStamp;
import com.b1.content.entity.Content;
import com.b1.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Builder(access = AccessLevel.PRIVATE)
    private Review(
            final String comment,
            final Integer rating,
            final ReviewStatus status,
            final User user,
            final Content content
    ) {
        this.comment = comment;
        this.rating = rating;
        this.status = status;
        this.user = user;
        this.content = content;
    }

    /**
     * 리뷰 등록
     */
    public static Review addReview(
            final String comment,
            final Integer rating,
            final User user,
            final Content content
    ) {
        return Review.builder()
                .comment(comment)
                .rating(rating)
                .status(ReviewStatus.ENABLE)
                .user(user)
                .content(content)
                .build();
    }

    /**
     * 리뷰 수정
     */
    public void updateReview(
            final String comment,
            final Integer rating
    ) {
        this.comment = comment;
        this.rating = rating;
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReview() {
        this.status = ReviewStatus.DISABLE;
    }
}
