package com.b1.ticket;

import com.b1.ticket.dto.TicketGetAllDto;
import com.b1.ticket.entity.TicketStatus;
import com.b1.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.b1.content.entity.QContent.content;
import static com.b1.round.entity.QRound.round;
import static com.b1.ticket.entity.QTicket.ticket;
import static com.b1.user.entity.QUser.user;


@Slf4j(topic = "Ticket Query Repository")
@Repository
@RequiredArgsConstructor
public class TicketQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     *
     */
    public Page<TicketGetAllDto> getAllTicketForUser(
            final User authUser,
            final Pageable pageable
    ) {
        List<TicketGetAllDto> roundList = jpaQueryFactory
                .select(Projections.constructor(
                        TicketGetAllDto.class,
                        content.id,
                        content.title,
                        content.status,
                        round.id,
                        round.sequence,
                        round.startDate,
                        round.startTime,
                        round.endTime,
                        round.status,
                        ticket.id,
                        ticket.price,
                        ticket.status
                ))
                .from(content)
                .join(round).on(content.id.eq(round.content.id))
                .join(ticket).on(ticket.round.id.eq(round.id))
                .join(user).on(user.id.eq(ticket.user.id))
                .where(
                        ticket.status.in(
                                TicketStatus.RESERVED,
                                TicketStatus.USED
                        ),
                        user.id.eq(authUser.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(ticket.createdAt.desc())
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(ticket.count())
                .from(content)
                .join(round).on(content.id.eq(round.content.id))
                .join(ticket).on(ticket.round.id.eq(round.id))
                .join(user).on(user.id.eq(ticket.user.id))
                .where(
                        ticket.status.in(
                                TicketStatus.RESERVED,
                                TicketStatus.USED
                        ),
                        user.id.eq(authUser.getId())
                );

        return PageableExecutionUtils.getPage(roundList, pageable, total::fetchOne);
    }

}
