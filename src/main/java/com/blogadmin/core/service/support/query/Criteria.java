package com.blogadmin.core.service.support.query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.blogadmin.core.utils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.blogadmin.core.model.BaseEntity;
import com.blogadmin.core.service.support.query.Restrictions.Restriction;
import com.blogadmin.core.service.support.query.annotation.Column;

public class Criteria {

    private static final Log              LOG = LogFactory.getLog(Criteria.class);
    protected Class<? extends BaseEntity> entityClass;
    protected List<Criterion>             criterions;

    public static Criteria create(Class<? extends BaseEntity> entityClass) {
        return new Criteria(entityClass);
    }

    protected Criteria(Class<? extends BaseEntity> entityClass) {
        this.entityClass = entityClass;
        criterions = new ArrayList<Criterion>();
    }

    public boolean isValid() {
        return criterions.size() > 0;
    }

    public List<Criterion> getAllCriteria() {
        return criterions;
    }

    public List<Criterion> getCriteria() {
        return criterions;
    }

    public void add(Restrictions r) {
        Restriction res = r.getRestriction();
        String property = r.getProperty();
        Object[] values = r.getValues();
        String columnName = getColumnName(property);
        if (Restriction.like.equals(res)) {
            addCriterion(columnName + " like", values[0], property);
        } else if (Restriction.eq.equals(res)) {
            addCriterion(columnName + " =", values[0], property);
        } else if (Restriction.between.equals(res)) {
            addCriterion(columnName + " between", values[0], values[1], property);
        }
    }

    private String getColumnName(String property) {
        Field f = null;
        try {
            f = entityClass.getDeclaredField(property);
        } catch (Exception e) {
            try {
                f = entityClass.getSuperclass().getDeclaredField(property);
            } catch (Exception e1) {
                LOG.error(e1);
            }
        }
        Assert.notNull(f);
        Column col = f.getAnnotation(Column.class);
        if (col != null)
            return col.name();
        else {
            return BeanUtils.separateClassName(property, "_", true);
        }

    }

    protected void addCriterion(String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criterions.add(new Criterion(condition));
    }

    protected void addCriterion(String condition, Object value, String property) {
        if (value != null) {
            criterions.add(new Criterion(condition, value));
        }

    }

    protected void addCriterion(String condition, Object value1, Object value2, String property) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + property + " cannot be null");
        }
        criterions.add(new Criterion(condition, value1, value2));
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

}
