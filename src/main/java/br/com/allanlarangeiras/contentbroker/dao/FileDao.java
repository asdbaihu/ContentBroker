package br.com.allanlarangeiras.contentbroker.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.allanlarangeiras.contentbroker.model.FileEntity;

@Component
public class FileDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void saveFile(FileEntity entity) {
		String sql = "INSERT INTO pdf_content (uuid, name, length, lastModified, data) values (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, entity.getUuid(), entity.getName(), entity.getLength(), entity.getLasModified(), entity.getFile());
	}
	
}
