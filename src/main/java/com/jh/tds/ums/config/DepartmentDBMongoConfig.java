package com.jh.tds.ums.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.jh.tds.ums.department",  // Specify the package for repository classes
        mongoTemplateRef = "DepartmentDBMongoConfig"      // Refer to the MongoTemplate bean for Database A
)
public class DepartmentDBMongoConfig {

    // Inject the URI for MongoDB Database B from application.properties
    @Value("${department.spring.data.mongodb.uri}")
    private String databaseBUri;

    // Bean for MongoTemplate for Database B
    @Qualifier("DepartmentDBMongoConfig")
    @Bean(name = "DepartmentDBMongoConfig")
    public MongoTemplate DepartmentDBMongoConfig() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(databaseBUri));
    }
}
