package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.SingleResponseError;
import ru.clevertec.ecl.exception.SqlException;
import ru.clevertec.ecl.repository.mappers.GiftCertificateSQLMapper;
import ru.clevertec.ecl.repository.entity.GiftCertificateEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@UtilityClass
public class QueryUtil {

    private static final Logger logger = LogManager.getLogger(QueryUtil.class);

    public static String getUpdateQuery(Map<String, Object> values, String tableName) {
        StringBuilder builder = new StringBuilder();
        values.keySet().stream().filter(key -> !"id".equals(key))
                .forEach(key -> builder.append(key).append(" = ").append(":").append(key).append(", "));
        builder.delete(builder.length() - 2, builder.length());
        return "update " + tableName + " set " + builder.toString().trim() + " where id = :id";
    }

    public static long executeQuery(String sqlQuery, MapSqlParameterSource paramMap,
                                    NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sqlQuery, paramMap, keyHolder, new String[]{"id"});
            return keyHolder.getKeyAs(Long.class);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            throw new SqlException(SingleResponseError.of("Bad request to the database", 50001));
        }
    }

    public static List<GiftCertificateEntity> executeFilteredQuery(GiftCertificateFilter filter,
                                                                   NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        String tagName = filter.getTagName();
        String fieldPart = filter.getFieldPart();
        String sortName = filter.getSortName();
        String sortDate = filter.getSortDate();
        StringBuilder builder = new StringBuilder();
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        if (fieldPart != null) {
            builder.append("select * from pattern_find_certificates(:field_part)");
            paramMap.addValue("field_part", "%" + fieldPart + "%");
        } else {
            builder.append("select * from find_certificates()");
        }

        if (tagName != null) {
            builder.append("\nWHERE id IN (\n" +
                    "SELECT gift_certificates.id\n" +
                    "FROM ecl.gift_certificates\n" +
                    "LEFT JOIN ecl.gift_certificates_tags ON ecl.gift_certificates.id = " +
                    "ecl.gift_certificates_tags.gift_certificate_id\n" +
                    "LEFT JOIN ecl.tags ON ecl.gift_certificates_tags.tag_id = ecl.tags.id\n" +
                    "WHERE ecl.tags.name = :tag_name\n" +
                    ")");
            paramMap.addValue("tag_name", tagName);
        }

        if (sortName != null || sortDate != null) {
            builder.append("\norder by ");

            if (sortName != null && sortDate == null) {
                builder.append("name ").append(sortName);
            } else if (sortName == null && sortDate != null) {
                builder.append("create_date ").append(sortDate);
            } else {
                builder.append("name ").append(sortName).append(", ").append("create_date ").append(sortDate);
            }
        }

        try {
            return namedParameterJdbcTemplate.query(builder.toString(), paramMap, new GiftCertificateSQLMapper()).get(0);
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }

    }

}
