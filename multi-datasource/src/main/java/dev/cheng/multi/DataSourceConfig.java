package dev.cheng.multi;

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
    private final String driverClassName;

    public DataSourceConfig(
            @Value("${spring.datasource.url}") String baseUrl,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverClassName) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    @Bean("hacnDataSource")
    public DataSource hacnDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(baseUrl + "?currentSchema=hacn");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setPoolName("HacnPool");
        return dataSource;
    }

    @Bean("hbcnDataSource")
    public DataSource hbcnDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(baseUrl + "?currentSchema=hbcn");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setPoolName("HbcnPool");
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(
            @Qualifier("hacnDataSource") DataSource hacnDataSource,
            @Qualifier("hbcnDataSource") DataSource hbcnDataSource
    ) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(SchemaType.HACN, hacnDataSource);
        targetDataSources.put(SchemaType.HBCN, hbcnDataSource);

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(hacnDataSource);

        return dynamicDataSource;
    }

}
