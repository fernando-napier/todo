/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.springframework.samples.dao;


        import com.microsoft.azure.spring.data.cosmosdb.repository.DocumentDbRepository;
        import com.microsoft.springframework.samples.model.TodoItem;
        import org.springframework.stereotype.Repository;


@Repository
public interface TodoItemRepository extends DocumentDbRepository<TodoItem, String> {
}
