package com.spreetail.sample.dao;

import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
import com.spreetail.sample.model.Subtask;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SubtaskRepository extends DocumentDbRepository<Subtask, String> {

    List<Subtask> findByTodoItemID(String todoItemID);
    List<Subtask> findByIdAndTodoItemID(String id, String todoItemID);
    void deleteByTodoItemID(String todoItemID);
    List<Subtask> findByTodoItemIDAndDescriptionAndProgressTypeIn(String todoItemID, String description,
                                                                  List<String> progressType);
}

