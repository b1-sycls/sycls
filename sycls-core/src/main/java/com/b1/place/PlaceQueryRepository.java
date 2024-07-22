package com.b1.place;

import static com.b1.place.entity.QPlace.place;

import com.b1.place.dto.PlaceGetResponseDto;
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
public class PlaceQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<PlaceGetResponseDto> getAllPlaces(final String location, final String name,
            final Integer maxSeat, final Pageable pageable) {

        List<PlaceGetResponseDto> placeList = jpaQueryFactory
                .select(Projections.constructor
                        (PlaceGetResponseDto.class,
                                place.id,
                                place.location,
                                place.name,
                                place.maxSeat,
                                place.status
                        )
                )
                .from(place)
                .where(
                        locationLike(location),
                        nameLike(name),
                        maxSeatEq(maxSeat)
                )
                .offset(pageable.getOffset()) //TODO NO offset 구조로 변경 예정
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(place.count())
                .from(place)
                .where(
                        locationLike(location),
                        nameLike(name),
                        maxSeatEq(maxSeat)
                );

        return PageableExecutionUtils.getPage(placeList, pageable, total::fetchOne);
    }

    private BooleanExpression locationLike(final String location) {
        return StringUtils.hasText(location) ? place.location.like(location) : null;
    }

    private BooleanExpression nameLike(final String name) {
        return StringUtils.hasText(name) ? place.name.like(name) : null;
    }

    private BooleanExpression maxSeatEq(final Integer maxSeat) {
        return maxSeat != null ? place.maxSeat.eq(maxSeat) : null;
    }

}
