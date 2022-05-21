package io.github.matej26.r2dbc.query.impl;

import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryExecutor;
import io.github.matej26.r2dbc.query.SortingQuery;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
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
        return this.findAll(null, null, clazz);
    }

    @Override
    public <T> Flux<T> findAll(Query query, Class<T> clazz) {
        return this.findAll(query, null, clazz);
    }

    @Override
    public <T> Flux<T> findAll(SortingQuery sortingQuery, Class<T> clazz) {
        return this.findAll(null, sortingQuery, clazz);
    }

    @Override
    public <T> Flux<T> findAll(Query query, SortingQuery sortingQuery, Class<T> clazz) {
        org.springframework.data.relational.core.query.Query finalQuery;
        if (query != null && sortingQuery != null) {
            finalQuery = query(query.get());
            return buildSortAndPage(sortingQuery, finalQuery, clazz);
        } else if (query != null) {
            return template.select(query(query.get()), clazz);
        } else if (sortingQuery != null) {
            finalQuery = query(Criteria.empty());
            return buildSortAndPage(sortingQuery, finalQuery, clazz);
        }
        return template.select(clazz).all();
    }

    private <T> Flux<T> buildSortAndPage(SortingQuery sortingQuery,
                                         org.springframework.data.relational.core.query.Query finalQuery,
                                         Class<T> clazz) {
        if (sortingQuery.getSort() != null) finalQuery = finalQuery.sort(sortingQuery.getSort());
        if (sortingQuery.getLimit() >= 0) finalQuery = finalQuery.limit(sortingQuery.getLimit());
        if (sortingQuery.getOffset() >= 0L) finalQuery = finalQuery.offset(sortingQuery.getOffset());

        return template.select(finalQuery, clazz);
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
