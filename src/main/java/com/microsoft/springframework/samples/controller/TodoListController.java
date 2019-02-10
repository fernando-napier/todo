/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.springframework.samples.controller;

import com.microsoft.springframework.samples.dao.SubtaskRepository;
import com.microsoft.springframework.samples.dao.TodoItemRepository;
import com.microsoft.springframework.samples.model.ProgressType;
import com.microsoft.springframework.samples.model.Subtask;
import com.microsoft.springframework.samples.model.TodoItem;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TodoListController {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    public TodoListController() {
    }

    @RequestMapping("/home")
    public Map<String, Object> home() {
        System.out.println(new Date() + " ======= /home =======");
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "home");
        return model;
    }

    /**
     * HTTP GET SUBTASKS
     */
    @RequestMapping(value = "/api/todolist/{todolistID}/subtask/{subtaskID}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getSubtask(@PathVariable("todolistID") String todolistID,
                                        @PathVariable("subtaskID") String subtaskID) {
        System.out.println(new Date() + " GET ======= /api/todolist/{" + todolistID + "}/subtask/{" + subtaskID + "}=======");
        try {
            return new ResponseEntity<>(subtaskRepository.findByIdAndTodoItemID(subtaskID, todolistID), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(subtaskID + " not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET TODOITEMS
     */
    @RequestMapping(value = "/api/todolist/{index}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem(@PathVariable("index") String todolistID) {
        System.out.println(new Date() + " GET ======= /api/todolist/{" + todolistID
                + "} =======");


        try {
            return new ResponseEntity<>(todoItemRepository.findById(todolistID), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(todolistID + " not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET ALL SUBTASKS
     */
    @RequestMapping(value = "/api/todolist/{todoListID}/subtask",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllSubtasks(@PathVariable String todoListID) {
        System.out.println(new Date() + " GET ======= /api/todolist =======");
        try {

            return new ResponseEntity<>(subtaskRepository.findByTodoItemID(todoListID), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET ALL TODOLISTS
     */
    //
    @RequestMapping(value = "/api/todolist",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTodoItems(@RequestParam("activeFlag") String activeString) {
        System.out.println(new Date() + " GET ======= /api/todolist =======");

        Boolean activeFlag = null;

        if (StringUtils.isNotBlank(activeString)) {
            activeFlag = Boolean.valueOf(activeString);
        }

        try {
            if (activeFlag == null) {
                return new ResponseEntity<>(todoItemRepository.findAll(new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            } else if (activeFlag == true) {
                List<String> list = new ArrayList<String>() {{
                    add(ProgressType.NOT_STARTED.toString());
                    add(ProgressType.IN_PROGRESS.toString());
                    add(ProgressType.ALMOST_DONE.toString());
                }};

                return new ResponseEntity<>(todoItemRepository.findByProgressTypeIn(list, new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            } else if (activeFlag == false) {
                return new ResponseEntity<>(todoItemRepository.findByProgressType(ProgressType.COMPLETED, new Sort(Sort.Direction.ASC, "dueDate")), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(todoItemRepository.findAll(new Sort(Sort.Direction.DESC, "priorityType")), HttpStatus.OK);
            }


        } catch (Exception e) {
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/todolist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTodoItem(@RequestBody TodoItem item) {
        System.out.println(new Date() + " POST ======= /api/todolist ======= " + item);
        try {
            item.setID(UUID.randomUUID().toString());
            todoItemRepository.save(item);
            return new ResponseEntity<String>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @RequestMapping(value = "/api/todolist", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody TodoItem item) {
        System.out.println(new Date() + " PUT ======= /api/todolist ======= " + item);
        try {
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
    @RequestMapping(value = "/api/todolist/{id}", method = RequestMethod.DELETE)
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
    @RequestMapping(value = "/api/todolist/{todolistID}/subtask", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewSubtask(@RequestBody Subtask subtask) {
        System.out.println(new Date() + " POST ======= /api/todolist ======= " + subtask);
        try {
            subtask.setID(UUID.randomUUID().toString());
            subtaskRepository.save(subtask);
            return new ResponseEntity<String>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }


    /**
     * HTTP PUT UPDATE
     */
    @RequestMapping(value = "/api/todolist/{todoID}/subtask/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateSubtask(String todoID, @RequestBody Subtask subtask) {
        System.out.println(new Date() + " PUT ======= /api/todolist ======= " + subtask);
        try {
            TodoItem todoItem = todoItemRepository.findById(todoID).get();

            if (todoItem.getID().equalsIgnoreCase(subtask.getTodoItemID())) {
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
    @RequestMapping(value = "/api/todolist/{todolistID}/subtask/{subtaskID}", method = RequestMethod.DELETE)
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
