package ru.clevertec.ecl.repository;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.api.ITagRepository;
import ru.clevertec.ecl.repository.mappers.TagSQLMapper;
import ru.clevertec.ecl.repository.util.QueryUtil;
import ru.clevertec.ecl.repository.entity.TagEntity;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements ITagRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger logger = LogManager.getLogger(TagRepository.class);

    public TagRepository(@Qualifier("dataSource") DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public long create(TagEntity tag) {
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("name", tag.getName());
        String sqlInsertQuery = "insert into ecl.tags (name) values (:name)";
        long tagId = QueryUtil.executeQuery(sqlInsertQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Created tag with id " + tagId);
        return tagId;
    }

    @Override
    public Optional<TagEntity> getById(long id) {
        String sqlSelectQuery = "select * from ecl.tags where id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        return Optional.ofNullable(this.getEntity(sqlSelectQuery, paramMap));
    }

    @Override
    public Optional<TagEntity> getByName(String name) {
        String sqlSelectQuery = "select * from ecl.tags where name = :name";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("name", name);
        return Optional.ofNullable(this.getEntity(sqlSelectQuery, paramMap));
    }

    @Override
    public List<TagEntity> getAll() {
        String sqlSelectQuery = "select * from ecl.tags";
        try {
            return this.namedParameterJdbcTemplate.query(sqlSelectQuery, new TagSQLMapper()).get(0);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public long update(long id, TagEntity updated) {
        String sqlUpdateQuery = "update ecl.tags set name = :updated_name where id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        paramMap.addValue("updated_name", updated.getName());
        long tagId = QueryUtil.executeQuery(sqlUpdateQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Updated tag with id" + tagId);
        return tagId;
    }

    @Override
    public void delete(long id) {
        String sqlDeleteQueryTemporalTable = "delete from ecl.gift_certificates_tags where tag_id = :id";
        String sqlDeleteQuery = "delete from ecl.tags where id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource();
        paramMap.addValue("id", id);
        QueryUtil.executeQuery(sqlDeleteQueryTemporalTable, paramMap, this.namedParameterJdbcTemplate);
        QueryUtil.executeQuery(sqlDeleteQuery, paramMap, this.namedParameterJdbcTemplate);
        logger.info("Deleted tag with id " + id);
    }

    @Override
    public void delete() {
        String sqlDeleteQuery = "delete from ecl.gift_certificates_tags;\ndelete from ecl.tags;";
        QueryUtil.executeQuery(sqlDeleteQuery, new MapSqlParameterSource(), this.namedParameterJdbcTemplate);
    }

    private TagEntity getEntity(String sqlSelectQuery, MapSqlParameterSource paramMap) {
        try {
            List<TagEntity> content = this.namedParameterJdbcTemplate.queryForObject(sqlSelectQuery, paramMap, new TagSQLMapper());
            if (content != null && content.size() == 1) {
                return content.get(0);
            } else {
                return null;
            }
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

}
