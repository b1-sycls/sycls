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

    private final JPAQueryFactory queryFactory;

    public List<ContentDetailImage> getByContentDetailImagesByContentId(Long contentId) {

        QContent content = QContent.content;
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;

        return queryFactory
                .selectFrom(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId))
                .fetch();
    }

    public List<ContentDetailImagePathGetAdminResponseDto> getAllContentDetailImagesPathByContentIdForAdmin(
            Long contentId) {

        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;
        QContent content = QContent.content;

        return queryFactory
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

    public List<ContentDetailImagePathGetUserResponseDto> getAllContentDetailImagesPathByContentIdForUser(
            Long contentId) {
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;
        QContent content = QContent.content;

        return queryFactory
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

    public ContentGetAdminResponseDto getByContentByContentIdForAdmin(Long contentId) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        return queryFactory
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

    public ContentGetUserResponseDto getByContentByContentIdForUser(Long contentId) {
        QContent content = QContent.content;
        QCategory category = QCategory.category;

        return queryFactory
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

    public Page<ContentGetAdminResponseDto> getAllContentForAdmin(Long categoryId,
            String titleKeyword,
            ContentStatus status, Pageable pageable) {

        QContent content = QContent.content;
        QCategory category = QCategory.category;

        List<ContentGetAdminResponseDto> contentList = queryFactory
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

        JPAQuery<Long> total = queryFactory
                .select(content.count())
                .from(content)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(status)
                );

        return PageableExecutionUtils.getPage(contentList, pageable, total::fetchOne);
    }

    public Page<ContentGetUserResponseDto> getAllContentForUser(Long categoryId,
            String titleKeyword, Pageable pageable) {
        QContent content = QContent.content;
        QCategory category = QCategory.category;

        List<ContentGetUserResponseDto> contentList = queryFactory
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

        JPAQuery<Long> total = queryFactory
                .select(content.count())
                .from(content)
                .where(
                        categoryEq(categoryId),
                        titleContains(titleKeyword),
                        statusEq(ContentStatus.VISIBLE)
                );

        return PageableExecutionUtils.getPage(contentList, pageable, total::fetchOne);
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId == null ? null : QCategory.category.id.eq(categoryId);
    }

    private BooleanExpression titleContains(String titleKeyword) {
        return StringUtils.hasText(titleKeyword) ? QContent.content.title.containsIgnoreCase(
                titleKeyword) : null;
    }

    private BooleanExpression statusEq(ContentStatus status) {
        return status == null ? null : QContent.content.status.eq(status);
    }
}
