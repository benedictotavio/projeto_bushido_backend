package br.org.institutobushido.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    private String username = "delivery-user";
    private String password = "delivery-user";
    private String port = "27017";
    private String host = "localhost";
    private String db = "salesdata";

    @Override
    public MongoClient mongoClient() {
        String conString = "mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=admin";
        MongoClient mClient = MongoClients.create(conString);
        return mClient;
    }

    @Override
    protected String getDatabaseName() {
        return this.db;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoTemplate mt = new MongoTemplate(mongoClient(), getDatabaseName());
        return mt;
    }
}