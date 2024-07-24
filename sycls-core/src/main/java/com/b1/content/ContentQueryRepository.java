package com.b1.content;

import com.b1.content.entity.ContentDetailImage;
import com.b1.content.entity.QContent;
import com.b1.content.entity.QContentDetailImage;
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

    public List<ContentDetailImage> getByContentDetailImagesByContentId(Long contentId) {
        QContent content = QContent.content;
        QContentDetailImage contentDetailImage = QContentDetailImage.contentDetailImage;

        return queryFactory
                .selectFrom(contentDetailImage)
                .leftJoin(contentDetailImage.content, content)
                .where(content.id.eq(contentId))
                .fetch();
    }
}
