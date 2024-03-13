package com.leduyminh.metricsserver.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return mapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();

        source.setBasenames("i18n/messages");
        source.setDefaultEncoding("UTF-8");
        source.setDefaultLocale(new Locale("vi"));
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }

//    @Bean
//    public MongoClient mongoClient() {
//		return MongoClients.create("mongodb://10.14.140.39:27101/metrics_server?retryWrites=false");
//	}
//
//    @Bean
//	public MongoClientFactoryBean mongo() {
//		MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//		mongo.setHost("localhost");
//		return mongo;
//	}
//
//    @Bean
//	public MongoOperations mongoOps() {
//		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(), "data_mining");
//		return mongoOps;
//	}
//
//    @Bean
//	public MongoDatabaseFactory mongoDatabaseFactory() {
//		return new SimpleMongoClientDatabaseFactory(MongoClients.create(), "database");
//	}
//
//    @Bean
//	public MongoTemplate mongoTemplate() throws Exception {
//		return new MongoTemplate(mongoClient(), "data_mining");
//	}
}
