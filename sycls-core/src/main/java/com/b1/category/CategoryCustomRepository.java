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
public class CategoryCustomRepository {

    private final JPAQueryFactory queryFactory;

    public List<CategoryGetResponseDto> findAllOrderByNameAsc() {
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(
                                CategoryGetResponseDto.class,
                                category.id,
                                category.name
                        )
                )
                .from(category)
                .orderBy(category.name.asc())
                .fetch();
    }
}
