package io.github.matej26.r2dbc.query.util;

import org.springframework.data.relational.core.query.Criteria;

public class QueryInfo {
    private Criteria criteria;
    private String previous;

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
}
