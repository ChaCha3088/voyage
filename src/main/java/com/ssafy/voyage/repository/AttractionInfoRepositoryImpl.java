package com.ssafy.voyage.repository;

import static com.ssafy.voyage.entity.QAttractionInfo.attractionInfo;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.voyage.entity.AttractionInfo;
import com.ssafy.voyage.entity.QAttractionInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AttractionInfoRepositoryImpl implements AttractionCustom{
    private final EntityManager em;

    QAttractionInfo info = new QAttractionInfo("info");

    public List<AttractionInfo> findAll(AttractionInfo attractionInfo) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        if(attractionInfo == null) return query.selectFrom(info).fetch();

        if(attractionInfo.getSidoCode() == 0) {
			if(attractionInfo.getContentTypeId() == 0) {

                return query.selectFrom(info).fetch();

			} else {
                return query.
                selectFrom(info).
                where(info.contentTypeId.eq(attractionInfo.getContentTypeId())).
                fetch();
			}
		}
		else {
			if(attractionInfo.getContentTypeId() == 0) {

                return query.
                selectFrom(info).
                where(info.sidoCode.eq(attractionInfo.getSidoCode())).
                fetch();
			} else {
                return query.
                selectFrom(info).
                where(info.sidoCode.eq(attractionInfo.getSidoCode())
                .and(info.contentTypeId.eq(attractionInfo.getContentTypeId())))
                .fetch();
            }
        }
    }
}


