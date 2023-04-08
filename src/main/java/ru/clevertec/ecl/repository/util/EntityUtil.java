package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

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

        if (giftCertificate.getCreateDate() != null) {
            values.put("create_date", giftCertificate.getCreateDate());
        }

        if (giftCertificate.getLastUpdateDate() != null) {
            values.put("last_update_date", giftCertificate.getLastUpdateDate());
        }

        return values;
    }

}
