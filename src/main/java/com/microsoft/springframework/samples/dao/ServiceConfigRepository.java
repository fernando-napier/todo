package com.microsoft.springframework.samples.dao;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.microsoft.springframework.samples.model.ServiceConfig;
import com.microsoft.springframework.samples.model.Subtask;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ServiceConfigRepository extends DocumentDbRepository<ServiceConfig, String> {


    List<ServiceConfig> findByKey(String key);
}
