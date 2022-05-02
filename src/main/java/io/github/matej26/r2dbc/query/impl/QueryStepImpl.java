package io.github.matej26.r2dbc.query.impl;

import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryStep;
import io.github.matej26.r2dbc.query.util.QueryInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.relational.core.query.Criteria;

import java.util.Collection;

import static io.github.matej26.r2dbc.query.util.QueryConstants.AND;
import static io.github.matej26.r2dbc.query.util.QueryConstants.OR;

public class QueryStepImpl implements QueryStep {
    private final QueryInfo queryInfo;

    public QueryStepImpl(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    @Override
    public Query equal(String column, Object value) {
        addCriteria(Criteria.where(column).is(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query notEqual(String column, Object value) {
        addCriteria(Criteria.where(column).not(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query lessThan(String column, Object value) {
        addCriteria(Criteria.where(column).lessThan(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query greaterThan(String column, Object value) {
        addCriteria(Criteria.where(column).greaterThan(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query lessThanOrEqualTo(String column, Object value) {
        addCriteria(Criteria.where(column).lessThanOrEquals(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query greaterThanOrEqualTo(String column, Object value) {
        addCriteria(Criteria.where(column).greaterThanOrEquals(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query isTrue(String column) {
        addCriteria(Criteria.where(column).isTrue());
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query isFalse(String column) {
        addCriteria(Criteria.where(column).isFalse());
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query isNull(String column) {
        addCriteria(Criteria.where(column).isNull());
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query isNotNull(String column) {
        addCriteria(Criteria.where(column).isNotNull());
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query like(String column, Object value) {
        addCriteria(Criteria.where(column).like(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query notLike(String column, Object value) {
        addCriteria(Criteria.where(column).notLike(value));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query in(String column, Collection<?> values) {
        addCriteria(Criteria.where(column).in(values));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query in(String column, Object... values) {
        addCriteria(Criteria.where(column).in(values));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query notIn(String column, Collection<?> values) {
        addCriteria(Criteria.where(column).notIn(values));
        return new QueryImpl(queryInfo);
    }

    @Override
    public Query notIn(String column, Object... values) {
        addCriteria(Criteria.where(column).notIn(values));
        return new QueryImpl(queryInfo);
    }

    private void addCriteria(Criteria criteria) {
        if (StringUtils.isEmpty(queryInfo.getPrevious())) {
            queryInfo.setCriteria(criteria);
        } else if (AND.equals(queryInfo.getPrevious())) {
            queryInfo.setCriteria(queryInfo.getCriteria().and(criteria));
        } else if (OR.equals(queryInfo.getPrevious())) {
            queryInfo.setCriteria(queryInfo.getCriteria().or(criteria));
        }
    }
}
