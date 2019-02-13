/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

 var apiToken = "?key=d425fd05da11587c0e8d7611490bfb6e";

'use strict';
angular.module('todoApp')
    .factory('todoListSvc', ['$http', function ($http) {
        return {


            getItems: function (activeFlag) {
                return $http.get('api/todoitem' + apiToken + '&activeFlag=' + activeFlag);
            },
            getItem: function (id) {
                return $http.get('api/todoitem/' + id + apiToken);
            },
            postItem: function (item) {
                return $http.post('api/todoitem/' + apiToken, item);
            },
            putItem: function (item) {
                return $http.put('api/todoitem/' + apiToken, item);
            },
            deleteItem: function (id) {
                return $http({
                    method: 'DELETE',
                    url: 'api/todoitem/' + id + apiToken
                });
            },
            getSubtasks: function (todoID) {
                 return $http.get('api/todoitem/' + todoID + '/subtask' + apiToken);
             },
            getSubtask: function (todoID, id) {
                 return $http.get('api/todoitem/' + todoID + '/subtask/' + id + apiToken);
             },
             postSubtask: function (todoID, subtask) {
                 return $http.post('api/todoitem/' + todoID + '/subtask/' + apiToken, subtask);
             },
             putSubtask: function (todoID, subtask) {
                 return $http.put('api/todoitem/' + todoID + '/subtask/' + apiToken, subtask );
             },
             deleteSubtask: function (todoID, subtaskID) {
                 return $http({
                     method: 'DELETE',
                     url: 'api/todoitem/' + todoID + '/subtask' + subtaskID  + apiToken
                 });
             }
        };
    }]);
