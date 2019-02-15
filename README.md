# Spring Todo App

This Spring TODO app is a Java application
built using [Spring Boot](https://spring.io/projects/spring-boot), 
[Spring Data for 
Cosmos DB](https://docs.microsoft.com/en-us/java/azure/spring-framework/configure-spring-boot-starter-java-app-with-cosmos-db?view=azure-java-stable) and 
[Azure Cosmos DB](https://docs.microsoft.com/en-us/azure/cosmos-db/sql-api-introduction). 

## Requirements

| [Azure CLI](http://docs.microsoft.com/cli/azure/overview) | [Java 8](https://www.azul.com/downloads/azure-only/zulu) | [Maven 3](http://maven.apache.org/) | [Git](https://github.com/) |


To run the application, either clone this repo and run the spring boot app via an IDE like intellij, or visit https://maven-web-app-190215041432649.azurewebsites.net/

Fun Facts:
  * You cannot create subtasks via the front end, but the API does fully support it
  * The feature I added to this that I figured would be useful is an Archive page that shows a user the todos they've completed and when they were completed, also allows for users to re-open tasks if necessary 
  * The api is located at {{url}}/api/, here's the structure:
    * /api/todoitem?
    * /api/todoitem/{todoID}?
    * /api/todoitem/{todoID}/subtask?
    * /api/todoitem/{todoID}/subtask/{subtaskID}?
  * The api does have a key, if you don't supply the key you will be returned a 403 Access Forbidden response
    * the key is d425fd05da11587c0e8d7611490bfb6e
    

Tutorial Time

1) Either publish the local version or go to the Azure site, the following landing page will appear:

![landing](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/landing%20page.PNG)

2) Now click on 'TodoList' on the nav bar, it'll take you to a sample todo list 

![sample](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/todo%20list.PNG)


3) We will now 'login', where we pick up all the todos for a specific user (users not in the system will return no todos)

![login](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/login%20session.PNG)


4) To create a new todo, simply add some info about the todo and click the 'add' button

![create](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/create%20todo.PNG)


5) For existing todos, you can edit, delete, and 'complete' them

![edit](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/update%20todo.PNG)
deleted items are purged from the repo of todoitems
![delete](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/delete%20todo.PNG)
completed items are purged only from the active view, as now they have a status of COMPLETED
![complete](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/complete%20todo.PNG)


6) We can check the archive to see what has been completed for a user, and re-open todos if necessary


![completed](https://github.com/fernando-napier/todo/blob/master/How%20to%20use%20the%20site/complete%20todo%20undo.PNG)

