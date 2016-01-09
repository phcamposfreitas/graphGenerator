package com.generator.graph;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
@EnableAutoConfiguration	
@EnableMongoRepositories(basePackages = {"com.generator.core.repository"})
@ComponentScan(basePackages = { "com.generator.*" })
public class GraphGeneratorApplication {

	@Value("${spring.data.mongodb.uri}")
	private String uriMongo;
	
	
	@Bean(name = "myDb")
	public Mongo getMongo() throws UnknownHostException{
		return new MongoClient(new MongoClientURI(uriMongo));
	}
	
    public static void main(String[] args) {
        SpringApplication.run(GraphGeneratorApplication.class, args);
    }
    
    
}
