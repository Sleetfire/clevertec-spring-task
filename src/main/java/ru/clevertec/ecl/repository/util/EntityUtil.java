package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.util.DateUtil;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@UtilityClass
public class EntityUtil {

    public static Map<String, Object> getMap(GiftCertificateEntity giftCertificate) {
        Map<String, Object> values = new HashMap<>();

        if (nonNull(giftCertificate.getName())) {
            values.put("name", giftCertificate.getName());
        }

        if (nonNull(giftCertificate.getDescription())) {
            values.put("description", giftCertificate.getDescription());
        }

        if (nonNull(giftCertificate.getPrice())) {
            values.put("price", giftCertificate.getPrice());
        }

        if (nonNull(giftCertificate.getDuration())) {
            values.put("duration", giftCertificate.getDuration());
        }

        if (nonNull(giftCertificate.getLastUpdateDate())) {
            values.put("last_update_date", DateUtil.getCurrentDateISO8601());
        }

        return values;
    }

    /**
     * Method for modifying query by filter params
     * @param filter filter with searching params
     * @param cb query builder
     * @param root query root
     * @param criteriaQuery query
     */
    public static void filterQuery(GiftCertificateFilter filter, CriteriaBuilder cb, Root<GiftCertificateEntity> root,
                                   CriteriaQuery<GiftCertificateEntity> criteriaQuery) {
        String tagName = filter.getTagName();
        String fieldPart = filter.getFieldPart();
        String sortName = filter.getSortName();
        String sortDate = filter.getSortDate();

        Predicate lastPredicate = null;
        Predicate fieldPartPredicate = null;
        Predicate tagPredicate;

        //search by part of the field
        if (nonNull(fieldPart)) {
            fieldPart = "%" + fieldPart + "%";
            fieldPartPredicate = cb.or(cb.like(root.get("name"), fieldPart), cb.like(root.get("description"), fieldPart));
            lastPredicate = cb.and(fieldPartPredicate);
        }

        // search by tag name
        if (nonNull(tagName)) {
            Join join = root.join("tags");
            tagPredicate = cb.equal(join.get("name"), tagName);
            if (nonNull(fieldPartPredicate)) {
                lastPredicate = cb.and(fieldPartPredicate, tagPredicate);
            } else {
                lastPredicate = cb.and(tagPredicate);
            }
        }

        if (nonNull(lastPredicate)) {
            criteriaQuery.where(lastPredicate);
        } else {
            criteriaQuery.select(root);
        }

        // sort by certificate and create date asc
        if ((nonNull(sortName) && sortName.contains("asc")) && (nonNull(sortDate) && sortDate.contains("asc"))) {
            criteriaQuery.orderBy(cb.asc(root.get("name")), cb.asc(root.get("createDate")));
        }

        // sort by certificate and create date desc
        if ((nonNull(sortName) && sortName.contains("desc")) && (nonNull(sortDate) && sortDate.contains("desc"))) {
            criteriaQuery.orderBy(cb.desc(root.get("name")), cb.desc(root.get("createDate")));
        }

        // sort by certificate asc and create date desc
        if ((nonNull(sortName) && sortName.contains("asc")) && (nonNull(sortDate) && sortDate.contains("desc"))) {
            criteriaQuery.orderBy(cb.asc(root.get("name")), cb.desc(root.get("createDate")));
        }

        // sort by certificate desc and create date asc
        if ((nonNull(sortName) && sortName.contains("desc")) && (nonNull(sortDate) && sortDate.contains("asc"))) {
            criteriaQuery.orderBy(cb.desc(root.get("name")), cb.asc(root.get("createDate")));
        }

        // sort by certificate asc
        if ((nonNull(sortName) && sortName.contains("asc")) && isNull(sortDate)) {
            criteriaQuery.orderBy(cb.asc(root.get("name")));
        }

        // sort by certificate desc
        if ((nonNull(sortName) && sortName.contains("desc")) && isNull(sortDate)) {
            criteriaQuery.orderBy(cb.desc(root.get("name")));
        }

        // sort by create date asc
        if ((nonNull(sortDate) && sortDate.contains("asc")) && isNull(sortName)) {
            criteriaQuery.orderBy(cb.asc(root.get("createDate")));
        }

        // sort by create date desc
        if ((nonNull(sortDate) && sortDate.contains("desc")) && isNull(sortName)) {
            criteriaQuery.orderBy(cb.desc(root.get("createDate")));
        }

    }

}