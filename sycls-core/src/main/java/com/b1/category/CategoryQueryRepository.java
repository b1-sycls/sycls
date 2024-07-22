package com.b1.category;

import com.b1.category.dto.CategoryGetAdminResponseDto;
import com.b1.category.dto.CategoryGetUserResponseDto;
import com.b1.category.entity.CategoryStatus;
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

    public List<CategoryGetAdminResponseDto> findAllOrderByNameAscForAdmin() {
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(
                                CategoryGetAdminResponseDto.class,
                                category.id,
                                category.name,
                                category.status
                        )
                )
                .from(category)
                .orderBy(category.name.asc())
                .fetch();
    }

    public List<CategoryGetUserResponseDto> findAllOrderByNameAscForUser() {
        QCategory category = QCategory.category;

        return queryFactory
                .select(Projections.constructor(
                                CategoryGetUserResponseDto.class,
                                category.id,
                                category.name
                        )
                )
                .from(category)
                .where(category.status.eq(CategoryStatus.ENABLE))
                .orderBy(category.name.asc())
                .fetch();
    }
}
