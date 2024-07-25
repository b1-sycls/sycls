package com.b1.content;

import com.b1.category.entity.QCategory;
import com.b1.content.dto.ContentDetailImagePathGetResponseDto;
import com.b1.content.dto.ContentGetAdminResponseDto;
import com.b1.content.entity.ContentDetailImage;
import com.b1.content.entity.QContent;
import com.b1.content.entity.QContentDetailImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j(topic = "Content Query Repository")
@Repository
@RequiredArgsConstructor
public class ContentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ContentGetAdminResponseDto getByContentByContentId(Long contentId) {

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

    public List<ContentDetailImage> getByContentDetailImagesByContentId(Long contentId) {
        QContent content = QContent.content;
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;

        return queryFactory
                .selectFrom(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId))
                .fetch();
    }


    public List<ContentDetailImagePathGetResponseDto> getAllContentDetailImagesPathByContentId(
            Long contentId) {
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;
        QContent content = QContent.content;

        return queryFactory
                .select(Projections.constructor(
                        ContentDetailImagePathGetResponseDto.class,
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
}
