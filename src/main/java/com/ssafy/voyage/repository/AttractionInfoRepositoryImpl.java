package com.ssafy.voyage.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.voyage.dto.request.AttractionInfoRequestDto;
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
    public Optional<AttractionInfo> findByContentIdWithDetailAndDescription(int contentId) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return Optional.ofNullable(queryFactory
            .selectFrom(attractionInfo)
            .where(attractionInfo.contentId.eq(contentId))
            .leftJoin(attractionInfo.attractionDetail).fetchJoin()
            .leftJoin(attractionInfo.attractionDescription).fetchJoin()
            .fetchOne());
    }

    @Override
    public List<AttractionInfo> findWithNoOffset(AttractionInfoRequestDto attractionInfoRequestDto) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        return queryFactory
            .select(Projections.fields(AttractionInfo.class, attractionInfo.contentId, attractionInfo.contentTypeId, attractionInfo.firstImage, attractionInfo.title, attractionInfo.addr1, attractionInfo.latitude, attractionInfo.longitude))
            .from(attractionInfo)
            .where(
                ltId(attractionInfoRequestDto.getLastId()),
                eqSidoCode(attractionInfoRequestDto.getSidoCode()),
                eqContentTypeId(attractionInfoRequestDto.getContentTypeId()),
                likeTitle(attractionInfoRequestDto.getTitle())
            )
            .orderBy(attractionInfo.contentId.desc())
            .limit(getPageSize(attractionInfoRequestDto.getPageSize()))
            .fetch();
    }

    private BooleanExpression ltId(long lastId) {
        return lastId == 0 ? null : attractionInfo.contentId.lt(lastId);
    }

    private BooleanExpression eqSidoCode(int sidoCode) {
        return sidoCode == 0 ? null : attractionInfo.sidoCode.sidoCode.eq(sidoCode);
    }

    private BooleanExpression eqContentTypeId(int contentTypeId) {
        return contentTypeId == 0 ? null : attractionInfo.contentTypeId.contentTypeId.eq(contentTypeId);
    }

    private BooleanExpression likeTitle(String title) {
        return title.isBlank() ? null : attractionInfo.title.contains(title);
    }

    private int getPageSize(int pageSize) {
        return pageSize == 0 ? 10 : pageSize;
    }
}
