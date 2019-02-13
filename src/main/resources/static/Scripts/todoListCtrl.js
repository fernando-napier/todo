/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .controller('todoListCtrl', ['$scope', '$location', 'todoListSvc', function ($scope, $location, todoListSvc) {
        $scope.error = '';
        $scope.loadingMessage = '';
        $scope.todoList = null;
        $scope.editingInProgress = false;
        $scope.newTodoCaption = '';
        $scope.newTodoTitle = '';
        $scope.newTodoDescription = '';
        $scope.newTodoProgress = '';
        $scope.newTodoPriority = '';
        $scope.newTodoDueDate = '';
        $scope.subtasks = null;

        apiKey = "d425fd05da11587c0e8d7611490bfb6e";


        $scope.editInProgressTodo = {
            description: '',
            id: 0,
            finish: false
        };

        $scope.finishSwitch = function (todo) {
            todo.progressType = "COMPLETED";
            todo.completedDate = Date.now();

            todoListSvc.putItem(todo).error(function (err) {
                todo.finished = !todo.finished;
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.reOpenSwitch = function (todo) {
                    todo.progressType = "IN_PROGRESS";
                    todo.completedDate = null;

                    todoListSvc.putItem(todo).error(function (err) {
                        todo.finished = !todo.finished;
                        $scope.error = err;
                        $scope.loadingMessage = '';
                    })
                };

        $scope.taskCompleted = function (todo) {
            todo.progressType = "COMPLETED";

            todoListSvc.putItem(todo).error(function (err) {
                todo.finished = !todo.finished;
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.editSwitch = function (todo) {
            todo.edit = !todo.edit;
            if (todo.edit) {
                $scope.editInProgressTodo.title = todo.title;
                $scope.editInProgressTodo.description = todo.description;
                $scope.editInProgressTodo.id = todo.id;
                $scope.editInProgressTodo.finished = todo.finished;
                $scope.editingInProgress = true;
            } else {
                $scope.editingInProgress = false;
            }
        };

        $scope.toggleViewCreateSubtask = function (todo) {
                    todo.editTask = !todo.editTask;

                if (todo.editTask) {
                    $scope.editingInProgress = true;
                } else {
                    $scope.editingInProgress = false;
                }
         };

         $scope.finishCreateSubtask = function (todoItemID) {
                     function getUser() {
                         var user = localStorage.getItem('user') || 'unknown';
                         localStorage.setItem('user', user);
                         return user;
                     }

                      todo.editTask = !todo.editTask;

                     todoListSvc.postSubtask(todoItemID, {
                         'description': $scope.newTaskDescription,
                         'progressType': $scope.newTaskProgress,
                         'todoItemID': todoItemID,
                         'owner': getUser(),
                         'finish': 'false'
                     }).success(function (results) {
                         $scope.newTodoTitle  = '';
                         $scope.newTodoDescription  = '';
                         $scope.populate("true");
                         $scope.loadingMessage = results;
                         $scope.error = '';
                     }).error(function (err) {
                         $scope.error = err;
                         $scope.loadingMsg = '';
                     })
                 };

        $scope.editTask = function (task) {
            task.edit = !task.edit;
            if (task.edit) {
                $scope.editInProgressTodo.title = todo.title;
                $scope.editInProgressTodo.description = todo.description;
                $scope.editInProgressTodo.id = todo.id;
                $scope.editInProgressTodo.finished = todo.finished;
                $scope.editingInProgress = true;
            } else {
                $scope.editingInProgress = false;
            }
        };

        $scope.populate = function (activeFlag) {
            todoListSvc.getItems(activeFlag).success(function (results) {
                $scope.todoList = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.populateSubtasks = function (id) {
            todoListSvc.getSubtasks(id).success(function (results) {
                $scope.subtasks = results;
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.delete = function (id) {
            todoListSvc.deleteItem(id).success(function (results) {
                $scope.populate();
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };

        $scope.update = function (todo) {
            todoListSvc.putItem($scope.editInProgressTodo).success(function (results) {
                $scope.populate();
                $scope.editSwitch(todo);
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMessage = '';
            })
        };
        
        $scope.add = function () {
            function getUser() {
                var user = localStorage.getItem('user') || 'unknown';
                localStorage.setItem('user', user);
                return user;
            }

            todoListSvc.postItem({
                'title': $scope.newTodoTitle,
                'description': $scope.newTodoDescription,
                'progressType': $scope.newTodoProgress,
                'priorityType': $scope.newTodoPriority,
                'dueDate': $scope.newTodoDueDate,
                'owner': getUser(),
                'finish': 'false'
            }).success(function (results) {
                $scope.newTodoTitle  = '';
                $scope.newTodoDescription  = '';
                $scope.populate("true");
                $scope.loadingMessage = results;
                $scope.error = '';
            }).error(function (err) {
                $scope.error = err;
                $scope.loadingMsg = '';
            })
        };





    }]);
