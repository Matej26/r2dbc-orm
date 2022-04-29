package mv.orm.r2dbc.util;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class QueryBuilder<T> {
    private String sqlOperation;
    private String previousOperation = "empty";
    private Criteria allCriteria = Criteria.empty();
    private final R2dbcEntityTemplate template;

    public QueryBuilder(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public QueryBuilder<T> findOne() {
        sqlOperation = "selectOne";
        return this;
    }

    public QueryBuilder<T> findAll() {
        sqlOperation = "selectAll";
        return this;
    }

    public QueryBuilder<T> save() {
        sqlOperation = "insert";
        return this;
    }

    public QueryBuilder<T> update() {
        sqlOperation = "update";
        return this;
    }

    public QueryBuilder<T> delete() {
        sqlOperation = "delete";
        return this;
    }

    public QueryBuilder<T> deleteAll() {
        sqlOperation = "deleteAll";
        return this;
    }

    public QueryBuilder<T> where() {
        return this;
    }

    public QueryBuilder<T> equal(String field, Object value) {
        Criteria criteria = Criteria.where(field).is(value);
        if (previousOperation.equals("empty")) {
            allCriteria = criteria;
        }
        if (previousOperation.equals("and")) {
            allCriteria = allCriteria.and(criteria);
        }
        if (previousOperation.equals("or")) {
            allCriteria = allCriteria.or(criteria);
        }
        return this;
    }

    public QueryBuilder<T> and() {
        previousOperation = "and";
        return this;
    }

    public QueryBuilder<T> or() {
        previousOperation = "or";
        return this;
    }

    public Mono<T> build(Class<T> type) {
        var query = Query.query(allCriteria);
        allCriteria = Criteria.empty();
        previousOperation = "empty";
        return template
                .select(type)
                .matching(query)
                .one();
    }
}
