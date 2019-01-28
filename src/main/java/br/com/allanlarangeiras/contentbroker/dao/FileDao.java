package br.com.allanlarangeiras.contentbroker.dao;

import br.com.allanlarangeiras.contentbroker.model.FileEntity;
import org.apache.activemq.util.ThreadTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class FileDao {

    private static final int LIMIT = 10;


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

	public void saveFile(FileEntity entity) {
		String sql = "INSERT INTO pdf_content (uuid, name, length, lastModified) values (:uuid, :name, :length, :lastModified)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("uuid", entity.getUuid());
		parameters.addValue("name", entity.getName());
		parameters.addValue("length", entity.getLength());
		parameters.addValue("lastModified", entity.getLasModified());
		jdbcTemplate.update(sql, parameters);
	}

	public boolean fileAlreadPersisted(FileEntity fileEntity) {
		String sql = "SELECT count(*) FROM pdf_content WHERE uuid = :uuid ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("uuid", fileEntity.getUuid());
		int result = jdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return result > 0;

	}

    public void removeFile(FileEntity fileEntity) {
        String sql = "DELETE FROM pdf_content where uuid = :uuid ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("uuid", fileEntity.getUuid());
        jdbcTemplate.update(sql, parameters);
    }

    public List<FileEntity> find(String uuid, int page) {
        StringBuilder sql = new StringBuilder("SELECT * FROM pdf_content");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        if (uuid != null) {
            sql.append(" WHERE uuid = :uuid ");
            parameters.addValue("uuid", uuid);
        }
        sql.append(" ORDER BY lastModified DESC LIMIT :offset, :limit");
        parameters.addValue("offset", getOffset(page));
        parameters.addValue("limit", LIMIT);

        List<FileEntity> result = jdbcTemplate.query(sql.toString(), parameters, new FileEntityRowMapper());

        return result;
    }

    private long getOffset(int page) {
        return (page - 1) * LIMIT;
    }


    private class FileEntityRowMapper implements RowMapper<FileEntity> {

        public FileEntity mapRow(ResultSet resultSet, int i) throws SQLException {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setUuid(resultSet.getString("uuid"));
            fileEntity.setName(resultSet.getString("name"));
            fileEntity.setLasModified(resultSet.getDate("lastModified"));
            fileEntity.setLength(resultSet.getLong("length"));
            return fileEntity;
        }
    }
}
