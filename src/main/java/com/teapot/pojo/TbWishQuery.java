package com.teapot.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TbWishQuery {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer pageNo = 1;

    protected Integer startRow;

    protected Integer pageSize = 10;

    protected String fields;

    public TbWishQuery() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo=pageNo;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setStartRow(Integer startRow) {
        this.startRow=startRow;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize=pageSize;
        this.startRow = (pageNo-1)*this.pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setFields(String fields) {
        this.fields=fields;
    }

    public String getFields() {
        return fields;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCustomeridIsNull() {
            addCriterion("customerId is null");
            return (Criteria) this;
        }

        public Criteria andCustomeridIsNotNull() {
            addCriterion("customerId is not null");
            return (Criteria) this;
        }

        public Criteria andCustomeridEqualTo(Integer value) {
            addCriterion("customerId =", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotEqualTo(Integer value) {
            addCriterion("customerId <>", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridGreaterThan(Integer value) {
            addCriterion("customerId >", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridGreaterThanOrEqualTo(Integer value) {
            addCriterion("customerId >=", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridLessThan(Integer value) {
            addCriterion("customerId <", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridLessThanOrEqualTo(Integer value) {
            addCriterion("customerId <=", value, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridIn(List<Integer> values) {
            addCriterion("customerId in", values, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotIn(List<Integer> values) {
            addCriterion("customerId not in", values, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridBetween(Integer value1, Integer value2) {
            addCriterion("customerId between", value1, value2, "customerid");
            return (Criteria) this;
        }

        public Criteria andCustomeridNotBetween(Integer value1, Integer value2) {
            addCriterion("customerId not between", value1, value2, "customerid");
            return (Criteria) this;
        }

        public Criteria andTempidIsNull() {
            addCriterion("tempId is null");
            return (Criteria) this;
        }

        public Criteria andTempidIsNotNull() {
            addCriterion("tempId is not null");
            return (Criteria) this;
        }

        public Criteria andTempidEqualTo(String value) {
            addCriterion("tempId =", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotEqualTo(String value) {
            addCriterion("tempId <>", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidGreaterThan(String value) {
            addCriterion("tempId >", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidGreaterThanOrEqualTo(String value) {
            addCriterion("tempId >=", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLessThan(String value) {
            addCriterion("tempId <", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLessThanOrEqualTo(String value) {
            addCriterion("tempId <=", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidLike(String value) {
            addCriterion("tempId like", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotLike(String value) {
            addCriterion("tempId not like", value, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidIn(List<String> values) {
            addCriterion("tempId in", values, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotIn(List<String> values) {
            addCriterion("tempId not in", values, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidBetween(String value1, String value2) {
            addCriterion("tempId between", value1, value2, "tempid");
            return (Criteria) this;
        }

        public Criteria andTempidNotBetween(String value1, String value2) {
            addCriterion("tempId not between", value1, value2, "tempid");
            return (Criteria) this;
        }

        public Criteria andWishIsNull() {
            addCriterion("wish is null");
            return (Criteria) this;
        }

        public Criteria andWishIsNotNull() {
            addCriterion("wish is not null");
            return (Criteria) this;
        }

        public Criteria andWishEqualTo(String value) {
            addCriterion("wish =", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishNotEqualTo(String value) {
            addCriterion("wish <>", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishGreaterThan(String value) {
            addCriterion("wish >", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishGreaterThanOrEqualTo(String value) {
            addCriterion("wish >=", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishLessThan(String value) {
            addCriterion("wish <", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishLessThanOrEqualTo(String value) {
            addCriterion("wish <=", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishLike(String value) {
            addCriterion("wish like", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishNotLike(String value) {
            addCriterion("wish not like", value, "wish");
            return (Criteria) this;
        }

        public Criteria andWishIn(List<String> values) {
            addCriterion("wish in", values, "wish");
            return (Criteria) this;
        }

        public Criteria andWishNotIn(List<String> values) {
            addCriterion("wish not in", values, "wish");
            return (Criteria) this;
        }

        public Criteria andWishBetween(String value1, String value2) {
            addCriterion("wish between", value1, value2, "wish");
            return (Criteria) this;
        }

        public Criteria andWishNotBetween(String value1, String value2) {
            addCriterion("wish not between", value1, value2, "wish");
            return (Criteria) this;
        }

        public Criteria andHoperIsNull() {
            addCriterion("hoper is null");
            return (Criteria) this;
        }

        public Criteria andHoperIsNotNull() {
            addCriterion("hoper is not null");
            return (Criteria) this;
        }

        public Criteria andHoperEqualTo(String value) {
            addCriterion("hoper =", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperNotEqualTo(String value) {
            addCriterion("hoper <>", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperGreaterThan(String value) {
            addCriterion("hoper >", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperGreaterThanOrEqualTo(String value) {
            addCriterion("hoper >=", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperLessThan(String value) {
            addCriterion("hoper <", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperLessThanOrEqualTo(String value) {
            addCriterion("hoper <=", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperLike(String value) {
            addCriterion("hoper like", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperNotLike(String value) {
            addCriterion("hoper not like", value, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperIn(List<String> values) {
            addCriterion("hoper in", values, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperNotIn(List<String> values) {
            addCriterion("hoper not in", values, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperBetween(String value1, String value2) {
            addCriterion("hoper between", value1, value2, "hoper");
            return (Criteria) this;
        }

        public Criteria andHoperNotBetween(String value1, String value2) {
            addCriterion("hoper not between", value1, value2, "hoper");
            return (Criteria) this;
        }

        public Criteria andSmsphoneIsNull() {
            addCriterion("smsPhone is null");
            return (Criteria) this;
        }

        public Criteria andSmsphoneIsNotNull() {
            addCriterion("smsPhone is not null");
            return (Criteria) this;
        }

        public Criteria andSmsphoneEqualTo(String value) {
            addCriterion("smsPhone =", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneNotEqualTo(String value) {
            addCriterion("smsPhone <>", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneGreaterThan(String value) {
            addCriterion("smsPhone >", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneGreaterThanOrEqualTo(String value) {
            addCriterion("smsPhone >=", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneLessThan(String value) {
            addCriterion("smsPhone <", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneLessThanOrEqualTo(String value) {
            addCriterion("smsPhone <=", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneLike(String value) {
            addCriterion("smsPhone like", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneNotLike(String value) {
            addCriterion("smsPhone not like", value, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneIn(List<String> values) {
            addCriterion("smsPhone in", values, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneNotIn(List<String> values) {
            addCriterion("smsPhone not in", values, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneBetween(String value1, String value2) {
            addCriterion("smsPhone between", value1, value2, "smsphone");
            return (Criteria) this;
        }

        public Criteria andSmsphoneNotBetween(String value1, String value2) {
            addCriterion("smsPhone not between", value1, value2, "smsphone");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNull() {
            addCriterion("created is null");
            return (Criteria) this;
        }

        public Criteria andCreatedIsNotNull() {
            addCriterion("created is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedEqualTo(Date value) {
            addCriterion("created =", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotEqualTo(Date value) {
            addCriterion("created <>", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThan(Date value) {
            addCriterion("created >", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedGreaterThanOrEqualTo(Date value) {
            addCriterion("created >=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThan(Date value) {
            addCriterion("created <", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedLessThanOrEqualTo(Date value) {
            addCriterion("created <=", value, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedIn(List<Date> values) {
            addCriterion("created in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotIn(List<Date> values) {
            addCriterion("created not in", values, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedBetween(Date value1, Date value2) {
            addCriterion("created between", value1, value2, "created");
            return (Criteria) this;
        }

        public Criteria andCreatedNotBetween(Date value1, Date value2) {
            addCriterion("created not between", value1, value2, "created");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}