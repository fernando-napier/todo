/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.spreetail.sample.dao;


        import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
        import com.spreetail.sample.model.ProgressType;
        import com.spreetail.sample.model.TodoItem;
        import org.springframework.data.domain.Sort;
        import org.springframework.stereotype.Repository;

        import java.util.List;


@Repository
public interface TodoItemRepository extends DocumentDbRepository<TodoItem, String> {


        List<TodoItem> findByOwnerAndProgressTypeIn(String owner, List<String> progressType, Sort sort);
        List<TodoItem> findByOwnerAndProgressType(String ownner, ProgressType progressType, Sort sort);
        List<TodoItem> findByOwnerOrderByPriorityType(String owner, Sort priorityType);
        List<TodoItem> findByOwnerAndTitleAndProgressTypeIn(String owner, String title, List<String> progressType);
 }
