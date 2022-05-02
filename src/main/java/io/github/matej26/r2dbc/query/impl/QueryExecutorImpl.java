package io.github.matej26.r2dbc.query.impl;

import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryExecutor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Query.query;

@Component
public class QueryExecutorImpl implements QueryExecutor {
    private final R2dbcEntityTemplate template;

    public QueryExecutorImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public <T> Mono<T> find(Query query, Class<T> clazz) {
        return template.selectOne(query(query.get()), clazz);
    }

    @Override
    public <T> Flux<T> findAll(Class<T> clazz) {
        return findAll(Query.where().isNotNull("id"), clazz);
    }

    @Override
    public <T> Flux<T> findAll(Query query, Class<T> clazz) {
        return template.select(query(query.get()), clazz);
    }

    @Override
    public <T> Mono<T> save(T entity) {
        return template.insert(entity);
    }

    @Override
    public <T> Mono<T> update(T entity) {
        return template.update(entity);
    }

    @Override
    public Mono<Integer> delete(Query query, Class<?> clazz) {
        return template.delete(query(query.get()), clazz);
    }
}
