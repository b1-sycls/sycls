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

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * (어드민) 카테고리 전체 조회
     */
    public List<CategoryGetAdminResponseDto> getAllOrderByNameAscForAdmin() {

        QCategory category = QCategory.category;

        return jpaQueryFactory
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

    /**
     * (유저) 카테고리 전체 조회
     */
    public List<CategoryGetUserResponseDto> getAllOrderByNameAscForUser() {

        QCategory category = QCategory.category;

        return jpaQueryFactory
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
