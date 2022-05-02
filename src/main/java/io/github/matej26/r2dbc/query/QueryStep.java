package io.github.matej26.r2dbc.query;

import java.util.Collection;

public interface QueryStep {
    Query equal(String column, Object value);

    Query notEqual(String column, Object value);

    Query lessThan(String column, Object value);

    Query greaterThan(String column, Object value);

    Query lessThanOrEqualTo(String column, Object value);

    Query greaterThanOrEqualTo(String column, Object value);

    Query isTrue(String column);

    Query isFalse(String column);

    Query isNull(String column);

    Query isNotNull(String column);

    Query like(String column, Object value);

    Query notLike(String column, Object value);

    Query in(String column, Collection<?> values);

    Query in(String column, Object... values);

    Query notIn(String column, Collection<?> values);

    Query notIn(String column, Object... values);
}
