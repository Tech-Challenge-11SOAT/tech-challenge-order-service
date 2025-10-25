package br.com.postech.techchallange_order.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.postech.techchallange_order.infrastructure.adapters.out.persistence")
@RequiredArgsConstructor
public class MongoConfig {

	private final MongoProperties properties;

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(properties.getUri());
	}

	@Bean
	@Primary
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		return new MongoTemplate(mongoClient, properties.getDatabase());
	}

	@Bean
	public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
}
