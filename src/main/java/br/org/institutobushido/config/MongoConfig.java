package br.org.institutobushido.config;

import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    // Adicionar variaveis de ambiente
    private static final String DB_NAME = "bushido";
    // Adicionar variaveis de ambiente
    private static final String MONGO_URI = "mongodb+srv://<username>:<password>@institutobushido.f8pdbuz.mongodb.net/?retryWrites=true&w=majority";

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(MONGO_URI))
                .applicationName(DB_NAME)
                .serverApi(serverApi)
                .build();

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            Document commandResult = database.runCommand(new BsonDocument("ping", new BsonInt64(1)));
            logger.info("Pinged your deployment. You successfully connected to MongoDB!");
            logger.info("Database: {}", DB_NAME);
            logger.info("Command Result: {}", commandResult);
            return mongoClient;
        } catch (MongoException me) {
            throw new MongoException("Error connecting to MongoDB: {} /n " + me.toString());
        }
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
