package br.com.allanlarangeiras.contentbroker.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class MysqlConfig {

	private static final String MYSQL_CLASS_DRIVER = "com.mysql.cj.jdbc.Driver";

	@Value("${mysql.connection.url}")
	private String url;

	@Value("${mysql.connection.username}")
	private String username;

	@Value("${mysql.connection.password}")	
	private String password;
	
	@Bean
	public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(MYSQL_CLASS_DRIVER);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
 
        return dataSource;
	}
	
}
