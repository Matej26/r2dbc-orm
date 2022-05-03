# R2DBC Query API
This module provides a couple of interfaces for database querying instead of standard R2DBC ```Criteria``` API. <br>
Working with all relational databases which have reactive driver (PostgreSQL, MySQL, Oracle, MS SQL, etc.).

## Concepts
The main idea is to give developer more control over the query like [JOOQ](https://www.jooq.org/) or 
[Criteria API](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/).  <br> <br>
```QueryStep``` interface supports basic SQL predicates: <br>
```=, !=, >, <, >=, <=, like, not like, in, not in, is true, is false, is null, is not null```. <br> <br>
```Query``` interface provides intermediate operators like ```where, or, and```. <br> <br>
And finally ```QueryExecutor``` interface allows to choose SQL operation: ```find, findAll, save, update, delete```. <br> <br>

## Usage
All you need is to inject ```QueryExecutor``` to your data access layer: <br>
```java
private final QueryExecutor executor;
```
And use it with ```Query```: <br>
```java
executor.findAll(Query.where().isNotNull("id"), Entity.class);
```
or without it: <br>
```java
executor.findAll(Entity.class);
```
```java
executor.save(entity);
```
