package com.ssafy.bjbj.api.challenge.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bjbj.api.challenge.dto.response.ChallengeMiniDto;
import com.ssafy.bjbj.api.challenge.dto.response.QChallengeMiniDto;
import com.ssafy.bjbj.common.enums.Status;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ssafy.bjbj.api.challenge.entity.QChallenge.*;
import static com.ssafy.bjbj.api.challenge.entity.QChallengeMember.*;

public class ChallengeRepositoryImpl implements ChallengeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ChallengeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Integer countChallengeMiniDto(boolean isAll) {
        JPAQuery<Integer> query = queryFactory
                .select(challenge.count().intValue())
                .from(challenge)
                .where(challenge.isDeleted.isFalse());

        if (!isAll) {
            query.where(challenge.status.eq(Status.PRE));
        }

        return query.fetchOne();
    }

    @Override
    public List<ChallengeMiniDto> findChallengeMiniDtos(boolean isAll, Pageable pageable) {
        JPAQuery<ChallengeMiniDto> query = queryFactory
                .select(new QChallengeMiniDto(
                        challenge.title,
                        challenge.deadline,
                        challenge.challengeMembers.size().intValue()))
                .from(challenge)
                .join(challenge.challengeMembers, challengeMember)
                .where(challenge.isDeleted.isFalse())
                .offset((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize());

        if (!isAll) {
            query.where(challenge.status.eq(Status.PRE));
        }

        return query.fetch();
    }

}
