package com.jh.tds.ums.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.jh.tds.ums.repository",  // Specify the package for repository classes
        mongoTemplateRef = "UserMGMTDBMongoConfig"      // Refer to the MongoTemplate bean for Database A
)
public class UserMGMTDBMongoConfig {

    // Inject the URI for MongoDB Database A from application.properties
    @Value("${spring.data.mongodb.uri}")
    private String databaseAUri;

    // Bean for MongoTemplate for Database A
    @Primary
    @Bean(name = "UserMGMTDBMongoConfig")
    public MongoTemplate UserMGMTDBMongoConfig() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(databaseAUri));
    }
}
