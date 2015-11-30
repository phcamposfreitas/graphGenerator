package com.generator.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.generator" })
public class GraphGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphGeneratorApplication.class, args);
    }
}
