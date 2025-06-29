package dev.cheng.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession
public class SessionConfig {
    @Bean
    @SpringSessionDataSource
    public DataSource springSessionDataSource(@Qualifier("publicDataSource") DataSource dataSource) {
        return dataSource;
    }
}
