package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.util.DateUtil;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class EntityUtil {

    public static Map<String, Object> getMap(GiftCertificateEntity giftCertificate) {
        Map<String, Object> values = new HashMap<>();

        if (giftCertificate.getName() != null) {
            values.put("name", giftCertificate.getName());
        }

        if (giftCertificate.getDescription() != null) {
            values.put("description", giftCertificate.getDescription());
        }

        if (giftCertificate.getPrice() != null) {
            values.put("price", giftCertificate.getPrice());
        }

        if (giftCertificate.getDuration() != null) {
            values.put("duration", giftCertificate.getDuration());
        }

        if (giftCertificate.getLastUpdateDate() != null) {
            values.put("last_update_date", DateUtil.getCurrentDateISO8601());
        }

        return values;
    }

    public static void filterQuery(GiftCertificateFilter filter, CriteriaBuilder cb, Root<GiftCertificateEntity> root,
                                   CriteriaQuery<GiftCertificateEntity> criteriaQuery) {
        String tagName = filter.getTagName();
        String fieldPart = filter.getFieldPart();
        String sortName = filter.getSortName();
        String sortDate = filter.getSortDate();

        Predicate lastPredicate = null;
        Predicate fieldPartPredicate = null;
        Predicate tagPredicate;

        if (fieldPart != null) {
            fieldPart = "%" + fieldPart + "%";
            fieldPartPredicate = cb.or(cb.like(root.get("name"), fieldPart), cb.like(root.get("description"), fieldPart));
            lastPredicate = cb.and(fieldPartPredicate);
        }

        if (tagName != null) {
            Join join = root.join("tags");
            tagPredicate = cb.equal(join.get("name"), tagName);
            if (fieldPartPredicate != null) {
                lastPredicate = cb.and(fieldPartPredicate, tagPredicate);
            } else {
                lastPredicate = cb.and(tagPredicate);
            }
        }

        if (lastPredicate != null) {
            criteriaQuery.where(lastPredicate);
        } else {
            criteriaQuery.select(root);
        }

        if ((sortName != null && sortName.contains("asc")) && (sortDate != null && sortDate.contains("asc"))) {
            criteriaQuery.orderBy(cb.asc(root.get("name")), cb.asc(root.get("createDate")));
        }

        if ((sortName != null && sortName.contains("desc")) && (sortDate != null && sortDate.contains("desc"))) {
            criteriaQuery.orderBy(cb.desc(root.get("name")), cb.desc(root.get("createDate")));
        }

        if ((sortName != null && sortName.contains("asc")) && (sortDate != null && sortDate.contains("desc"))) {
            criteriaQuery.orderBy(cb.asc(root.get("name")), cb.desc(root.get("createDate")));
        }

        if ((sortName != null && sortName.contains("desc")) && (sortDate != null && sortDate.contains("asc"))) {
            criteriaQuery.orderBy(cb.desc(root.get("name")), cb.asc(root.get("createDate")));
        }

        if ((sortName != null && sortName.contains("asc")) && (sortDate == null)) {
            criteriaQuery.orderBy(cb.asc(root.get("name")));
        }

        if ((sortName != null && sortName.contains("desc")) && (sortDate == null)) {
            criteriaQuery.orderBy(cb.desc(root.get("name")));
        }

        if ((sortDate != null && sortDate.contains("asc")) && (sortName == null)) {
            criteriaQuery.orderBy(cb.asc(root.get("createDate")));
        }

        if ((sortDate != null && sortDate.contains("desc")) && (sortName == null)) {
            criteriaQuery.orderBy(cb.desc(root.get("createDate")));
        }

    }

}