package com.generator.core.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.generator.core.model.SessaoEntity;

public interface SessaoRepository extends MongoRepository<SessaoEntity, String> {

}
