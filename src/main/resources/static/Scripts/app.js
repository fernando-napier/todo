/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

'use strict';
angular.module('todoApp', ['ngRoute'])
    .config(['$routeProvider',  function ($routeProvider) {
        $routeProvider.when('/Home', {
            controller: 'homeCtrl',
            templateUrl: 'Views/Home.html',
        }).when('/TodoList', {
            controller: 'todoListCtrl',
            templateUrl: 'Views/TodoList.html',
        }).when('/Archive', {
              controller: 'todoListCtrl',
              templateUrl: 'Views/Archive.html',
          }).otherwise({redirectTo: '/Home'});
    }]);
