package io.github.matej26.r2dbc.query.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Criteria;

public class QueryInfo {
    private Criteria criteria;
    private String previous;
    private int limit;
    private long offset;
    private Sort sort;

    public QueryInfo(Criteria criteria) {
        this.criteria = criteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
