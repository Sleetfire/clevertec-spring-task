package ru.clevertec.ecl.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSQLMapper implements RowMapper<List<GiftCertificateEntity>> {

    @Override
    public List<GiftCertificateEntity> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<GiftCertificateEntity> giftCertificates = new ArrayList<>();
        //rs.next();
        while (!rs.isAfterLast()) {
            GiftCertificateEntity giftCertificate = GiftCertificateEntity.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .price(rs.getBigDecimal("price"))
                    .duration(Duration.ofDays(rs.getInt("duration")))
                    .createDate(rs.getString("create_date"))
                    .lastUpdateDate(rs.getString("last_update_date"))
                    .build();

            List<TagEntity> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getLong("id") == giftCertificate.getId()) {
                long tagId = rs.getLong("tag_id");
                if (tagId != 0) {
                    TagEntity tag = TagEntity.builder()
                            .id(rs.getLong("tag_id"))
                            .name(rs.getString("tag_name"))
                            .build();
                    tags.add(tag);
                }
                rs.next();
            }
            giftCertificate.setTags(tags);
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
