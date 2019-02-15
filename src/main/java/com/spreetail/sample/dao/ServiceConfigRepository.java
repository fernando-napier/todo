package com.spreetail.sample.dao;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.spreetail.sample.model.ServiceConfig;

import java.util.List;

public interface ServiceConfigRepository extends DocumentDbRepository<ServiceConfig, String> {


    List<ServiceConfig> findByKey(String key);
}
