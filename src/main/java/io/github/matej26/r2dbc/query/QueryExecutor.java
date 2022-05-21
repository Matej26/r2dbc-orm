package io.github.matej26.r2dbc.query;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface QueryExecutor {
    <T> Mono<T> find(Query query, Class<T> clazz);

    <T> Flux<T> findAll(Class<T> clazz);

    <T> Flux<T> findAll(Query query, Class<T> clazz);

    <T> Flux<T> findAll(SortingQuery sortingQuery, Class<T> clazz);

    <T> Flux<T> findAll(Query query, SortingQuery sortingQuery, Class<T> clazz);

    <T> Mono<T> save(T entity);

    <T> Mono<T> update(T entity);

    Mono<Integer> delete(Query query, Class<?> clazz);
}
