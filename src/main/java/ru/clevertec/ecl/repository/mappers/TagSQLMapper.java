package ru.clevertec.ecl.repository.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.ecl.repository.entity.TagEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagSQLMapper implements RowMapper<List<TagEntity>> {

    @Override
    public List<TagEntity> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<TagEntity> tagEntities = new ArrayList<>();
        while (!rs.isAfterLast()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            TagEntity tagEntity = TagEntity.builder().id(id).name(name).build();
            tagEntities.add(tagEntity);
            rs.next();
        }
        return tagEntities;
    }
}
