package com.b1.category;

import com.b1.category.dto.CategoryGetResponseDto;
import com.b1.category.entity.QCategory;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CategoryGetResponseDto> findAllOrderByNameAsc() {
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(
                                CategoryGetResponseDto.class,
                                category.id,
                                category.name,
                                category.status
                        )
                )
                .from(category)
                .orderBy(category.name.asc())
                .fetch();
    }
}