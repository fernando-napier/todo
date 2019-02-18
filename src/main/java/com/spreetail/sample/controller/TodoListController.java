/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.spreetail.sample.controller;

import com.spreetail.sample.dao.SubtaskRepository;
import com.spreetail.sample.dao.TodoItemRepository;
import com.spreetail.sample.model.ProgressType;
import com.spreetail.sample.model.Subtask;
import com.spreetail.sample.model.TodoItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class TodoListController  {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    public TodoListController() {
    }


    private List<String> generateActiveProgressTypesList() {
        return new ArrayList<String>() {{
            add(ProgressType.NOT_STARTED.toString());
            add(ProgressType.IN_PROGRESS.toString());
            add(ProgressType.ALMOST_DONE.toString());
        }};
    }

    /**
     * HTTP GET ALL TODO Items
     */
    //
    @RequestMapping(value = "/api/todoitem",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTodoItems(@RequestParam("activeFlag") String activeString,
                                             @RequestParam("user") String user) {
        System.out.println(new Date() + " GET ======= /api/todolist =======");

        Boolean activeFlag = null;

        if (StringUtils.isNotBlank(activeString)) {
            activeFlag = Boolean.valueOf(activeString);
        }

        if (StringUtils.isBlank(user)) {
            user = "";
        }

        try {
            if (activeFlag == null) {
                return new ResponseEntity<>(todoItemRepository.findByOwnerOrderByPriorityType(user, new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            } else if (activeFlag == true) {
                List<String> list = this.generateActiveProgressTypesList();

                return new ResponseEntity<>(todoItemRepository.findByOwnerAndProgressTypeIn(user, list, new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            } else if (activeFlag == false) {
                return new ResponseEntity<>(todoItemRepository.findByOwnerAndProgressType(user, ProgressType.COMPLETED, new Sort(Sort.Direction.ASC, "dueDate")), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(todoItemRepository.findByOwnerOrderByPriorityType(user, new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            }


        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TodoItems Not Found", e);
        }
    }

    /**
     * HTTP GET SUBTASKS
     */
    @RequestMapping(value = "/api/todoitem/{todoItemID}/subtask/{subtaskID}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getSubtask(@PathVariable("todoItemID") String todolistID,
                                        @PathVariable("subtaskID") String subtaskID) {
        System.out.println(new Date() + " GET ======= /api/todolist/{" + todolistID + "}/subtask/{" + subtaskID + "}=======");
        try {
            return new ResponseEntity<>(subtaskRepository.findByIdAndTodoItemID(subtaskID, todolistID), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtask Not Found", e);
        }
    }

    /**
     * HTTP GET TODOITEMS
     */
    @RequestMapping(value = "/api/todoitem/{index}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem(@PathVariable("index") String todolistID) {
        System.out.println(new Date() + " GET ======= /api/todolist/{" + todolistID
                + "} =======");


        try {
            return new ResponseEntity<>(todoItemRepository.findById(todolistID), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtask Not Found", e);
        }
    }

    /**
     * HTTP GET ALL SUBTASKS
     */
    @RequestMapping(value = "/api/todoitem/{todoListID}/subtask",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllSubtasks(@PathVariable String todoListID) {
        System.out.println(new Date() + " GET ======= /api/todolist =======");
        try {

            return new ResponseEntity<>(subtaskRepository.findByTodoItemID(todoListID), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subtask Not Found", e);
        }
    }



    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/todoitem", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTodoItem(@RequestBody TodoItem item) {
        System.out.println(new Date() + " POST ======= /api/todolist ======= " + item);
        try {
            item.setID(UUID.randomUUID().toString());

            // check for active duplicate todos
            // typically would do this at the db level but cosmosdb doesn't allow for uniqueness
            List<TodoItem> items = todoItemRepository.findByOwnerAndTitleAndProgressTypeIn(
                    item.getOwner(), item.getTitle(), this.generateActiveProgressTypesList());

            if (!CollectionUtils.isEmpty(items)) {
                return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
            }
            todoItemRepository.save(item);
            return new ResponseEntity<String>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @RequestMapping(value = "/api/todoitem", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody TodoItem item) {
        System.out.println(new Date() + " PUT ======= /api/todolist ======= " + item);
        try {

            // check for active duplicate todos
            // in case there is an active todoitem with the same owner and title
            List<TodoItem> items = todoItemRepository.findByOwnerAndTitleAndProgressTypeIn(
                    item.getOwner(), item.getTitle(), this.generateActiveProgressTypesList());

            if (!CollectionUtils.isEmpty(items)) {
                if (items.size() > 1) {
                    return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
                } else if (!items.get(0).getID().equalsIgnoreCase(item.getID())){
                    return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
                }
            }

            todoItemRepository.deleteById(item.getID());
            todoItemRepository.save(item);
            return new ResponseEntity<String>("Entity updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity updating failed", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP DELETE
     */
    @RequestMapping(value = "/api/todoitem/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") String id) {
        System.out.println(new Date() + " DELETE ======= /api/todolist/{" + id
                + "} ======= ");
        try {
            todoItemRepository.deleteById(id);
            subtaskRepository.deleteByTodoItemID(id);
            return new ResponseEntity<String>("Entity deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity deletion failed", HttpStatus.NOT_FOUND);
        }

    }


    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/todoitem/{todoItemID}/subtask", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewSubtask(@PathVariable("todoItemID") String todoItemID, @RequestBody Subtask subtask) {
        System.out.println(new Date() + " POST ======= /api/todolist ======= " + subtask);
        try {
            subtask.setID(UUID.randomUUID().toString());
            subtask.setTodoItemID(todoItemID);

            List<Subtask> subtasks = subtaskRepository.findByTodoItemIDAndDescriptionAndProgressTypeIn(
              subtask.getTodoItemID(), subtask.getDescription(), this.generateActiveProgressTypesList());

            if (!CollectionUtils.isEmpty(subtasks)) {
                return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
            }

            subtaskRepository.save(subtask);
            return new ResponseEntity<String>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }


    /**
     * HTTP PUT for a subtask within a todoitem
     */
    @RequestMapping(value = "/api/todoitem/{todoID}/subtask/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSubtask(@PathVariable("todoID") String todoID, @RequestBody Subtask subtask) {
        System.out.println(new Date() + " PUT ======= /api/todolist ======= " + subtask);
        try {
            TodoItem todoItem = todoItemRepository.findById(todoID).get();

            if (todoItem.getID().equalsIgnoreCase(subtask.getTodoItemID())) {
                List<Subtask> subtasks = subtaskRepository.findByTodoItemIDAndDescriptionAndProgressTypeIn(
                        subtask.getTodoItemID(), subtask.getDescription(), this.generateActiveProgressTypesList());


                if (!CollectionUtils.isEmpty(subtasks)) {
                    if (subtasks.size() > 1) {
                        return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
                    } else if (!subtasks.get(0).getID().equalsIgnoreCase(subtask.getID())){
                        return new ResponseEntity<>("Duplicate entity found", HttpStatus.CONFLICT);
                    }
                }

                subtaskRepository.deleteById(subtask.getID());
                subtaskRepository.save(subtask);
                return new ResponseEntity<String>("Entity updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Entity updating failed", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>("Entity updating failed", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP DELETE
     */
    @RequestMapping(value = "/api/todoitem/{todolistID}/subtask/{subtaskID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteSubtask(@PathVariable("todolistID") String todolistID,
                                                @PathVariable("subtaskID") String subtaskID) {
        System.out.println(new Date() + " DELETE ======= /api/todolist/{" + todolistID + "}/subtask/{" + subtaskID
                + "} ======= ");
        try {
            List<Subtask> subtasks = subtaskRepository.findByIdAndTodoItemID(subtaskID, todolistID);

            if (CollectionUtils.isEmpty(subtasks)) {
                return new ResponseEntity<String>("Entity deletion failed", HttpStatus.NOT_FOUND);
            } else {
                subtaskRepository.deleteById(subtasks.get(0).getID());
                return new ResponseEntity<String>("Entity deleted", HttpStatus.OK);
            }


        } catch (Exception e) {
            return new ResponseEntity<String>("Entity deletion failed", HttpStatus.NOT_FOUND);
        }

    }
}
