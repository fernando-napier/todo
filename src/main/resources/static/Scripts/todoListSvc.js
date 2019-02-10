/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp')
    .factory('todoListSvc', ['$http', function ($http) {
        return {
            getItems: function () {
                return $http.get('api/todolist');
            },
            getItem: function (id) {
                return $http.get('api/todolist/' + id);
            },
            postItem: function (item) {
                return $http.post('api/todolist/', item);
            },
            putItem: function (item) {
                return $http.put('api/todolist/', item);
            },
            deleteItem: function (id) {
                return $http({
                    method: 'DELETE',
                    url: 'api/todolist/' + id
                });
            },
            getSubtasks: function (todoID) {
                 return $http.get('api/todolist/' + todoID + '/subtask');
             },
            getSubtask: function (todoID, id) {
                 return $http.get('api/todolist/' + todoID + '/subtask/' + id);
             },
             postSubtask: function (todoID, subtask) {
                 return $http.post('api/todolist/' + todoID + '/subtask/', subtask);
             },
             putSubtask: function (todoID, subtask) {
                 return $http.put('api/todolist/' + todoID + '/subtask/', subtask);
             },
             deleteItem: function (todoID, subtaskID) {
                 return $http({
                     method: 'DELETE',
                     url: 'api/todolist/' + todoID + '/subtask' + id
                 });
             }
        };
    }]);
