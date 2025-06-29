package dev.cheng.schema;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    private final String baseUrl;
    private final String username;
    private final String password;

    public DataSourceConfig(@Value("${spring.datasource.url}") String baseUrl,
                            @Value("${spring.datasource.username}") String username,
                            @Value("${spring.datasource.password}") String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }

    @Bean("publicDataSource")
    public DataSource publicDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(baseUrl + "?currentSchema=public");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setPoolName("HacnPool");
        return dataSource;
    }

    @Bean("hacnDataSource")
    public DataSource hacnDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(baseUrl + "?currentSchema=hacn");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setPoolName("HacnPool");
        return dataSource;
    }

    @Bean("hbcnDataSource")
    public DataSource hbcnDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(baseUrl + "?currentSchema=hbcn");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setPoolName("HbcnPool");
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(
            @Qualifier("publicDataSource") DataSource publicDataSource,
            @Qualifier("hacnDataSource") DataSource hacnDataSource,
            @Qualifier("hbcnDataSource") DataSource hbcnDataSource
    ) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(SchemaType.PUBLIC, publicDataSource);
        targetDataSources.put(SchemaType.HACN, hacnDataSource);
        targetDataSources.put(SchemaType.HBCN, hbcnDataSource);

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(publicDataSource);

        return dynamicDataSource;
    }

}
