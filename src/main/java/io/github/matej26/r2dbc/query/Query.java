package io.github.matej26.r2dbc.query;

import io.github.matej26.r2dbc.query.util.QueryInfo;
import io.github.matej26.r2dbc.query.impl.QueryStepImpl;
import org.springframework.data.relational.core.query.Criteria;

public interface Query {
    static QueryStep where() {
        QueryInfo queryInfo = new QueryInfo(Criteria.empty());
        return new QueryStepImpl(queryInfo);
    }

    QueryStep or();

    QueryStep and();

    Criteria get();
}
