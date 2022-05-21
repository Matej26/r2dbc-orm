package io.github.matej26.r2dbc.query;

import io.github.matej26.r2dbc.query.util.QueryInfo;
import io.github.matej26.r2dbc.query.util.SortingDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;

public class SortingQuery {
    private final QueryInfo queryInfo;

    public SortingQuery(QueryInfo queryInfo) {
        this.queryInfo = queryInfo;
    }

    public static SortingQuery sort(String column) {
        return sortAndPage(column, SortingDirection.ASC, -1, -1L);
    }

    public static SortingQuery sort(String column, SortingDirection value) {
        return sortAndPage(column, value, -1, -1L);
    }

    public static SortingQuery sortAndPage(String column, int limit) {
        return sortAndPage(column, SortingDirection.ASC, limit, -1L);
    }

    public static SortingQuery sortAndPage(String column, long offset) {
        return sortAndPage(column, SortingDirection.ASC, -1, offset);
    }

    public static SortingQuery sortAndPage(String column, int limit, long offset) {
        return sortAndPage(column, SortingDirection.ASC, limit, offset);
    }

    public static SortingQuery sortAndPage(String column, SortingDirection value, int limit) {
        return sortAndPage(column, value, limit, -1L);
    }

    public static SortingQuery sortAndPage(String column, SortingDirection value, long offset) {
        return sortAndPage(column, value, -1, offset);
    }

    public static SortingQuery sortAndPage(String column, SortingDirection value, int limit, long offset) {
        QueryInfo queryInfo = new QueryInfo(Criteria.empty());
        if (SortingDirection.ASC.equals(value)) {
            queryInfo.setSort(Sort.by(column).ascending());
        }
        if (SortingDirection.DESC.equals(value)) {
            queryInfo.setSort(Sort.by(column).descending());
        }
        queryInfo.setLimit(limit);
        queryInfo.setOffset(offset);
        return new SortingQuery(queryInfo);
    }

    public Sort getSort() {
        return queryInfo.getSort();
    }

    public int getLimit() {
        return queryInfo.getLimit();
    }

    public long getOffset() {
        return queryInfo.getOffset();
    }
}
