package com.generator.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.generator.api.ChaveValor;

public interface ChaveValorRepository extends MongoRepository<ChaveValor, String> {

}
