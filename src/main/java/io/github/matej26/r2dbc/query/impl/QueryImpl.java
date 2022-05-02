package io.github.matej26.r2dbc.query.impl;

import io.github.matej26.r2dbc.query.Query;
import io.github.matej26.r2dbc.query.QueryStep;
import io.github.matej26.r2dbc.query.util.QueryInfo;
import org.springframework.data.relational.core.query.Criteria;

import static io.github.matej26.r2dbc.query.util.QueryConstants.AND;
import static io.github.matej26.r2dbc.query.util.QueryConstants.OR;

public class QueryImpl implements Query {
    private final QueryInfo queryInfo;

    QueryImpl(QueryInfo queryInfo) {
     this.queryInfo = queryInfo;
    }

    public QueryInfo getQueryInfo() {
        return queryInfo;
    }

    @Override
    public QueryStep or() {
        queryInfo.setPrevious(OR);
        return new QueryStepImpl(queryInfo);
    }

    @Override
    public QueryStep and() {
        queryInfo.setPrevious(AND);
        return new QueryStepImpl(queryInfo);
    }

    @Override
    public Criteria get() {
        return this.getQueryInfo().getCriteria();
    }
}
