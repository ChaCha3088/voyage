package com.ssafy.voyage.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.voyage.entity.AttractionInfo;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.ssafy.voyage.entity.QAttractionInfo.attractionInfo;

@RequiredArgsConstructor
public class AttractionInfoRepositoryImpl implements AttractionInfoRepositoryQueryDsl {
    private final EntityManager em;

    @Override
    public List<AttractionInfo> findAttractionInfoNoOffset(long lastId, int sidoCode, int contentTypeId, String title, int pageSize) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
                .selectFrom(attractionInfo)
                .where(
                    ltId(lastId),
                    eqSidoCode(sidoCode),
                    eqContentTypeId(contentTypeId),
                    likeTitle(title)
                )
                .join(attractionInfo.attractionDetail).fetchJoin()
                .join(attractionInfo.attractionDescription).fetchJoin()
                .orderBy(attractionInfo.contentId.desc())
                .limit(getPageSize(pageSize))
            .fetch();
    }

    private BooleanExpression ltId(long lastId) {
        return lastId == 0 ? null : attractionInfo.contentId.lt(lastId);
    }

    private BooleanExpression eqSidoCode(int sidoCode) {
        return sidoCode == 0 ? null : attractionInfo.sidoCode.sidoCode.eq(sidoCode);
    }

    private BooleanExpression eqContentTypeId(int contentTypeId) {
        return contentTypeId == 0 ? null : attractionInfo.contentTypeId.eq(contentTypeId);
    }

    private BooleanExpression likeTitle(String title) {
        return title.isBlank() ? null : attractionInfo.title.contains(title);
    }

    private int getPageSize(int pageSize) {
        return pageSize == 0 ? 10 : pageSize;
    }
}
