package com.blogadmin.core.service.support.query;

public class Restrictions {

    public enum Restriction {
        like,
        between,
        eq
    }

    private Restriction r;
    private String      property;
    private Object[]    values;

    public Restrictions(Restriction r, String property, Object... values) {
        this.r = r;
        this.property = property;
        this.values = values;
    }

    public static Restrictions like(String property, String value) {
        return new Restrictions(Restriction.like, property, value);
    }

    public static Restrictions between(String property, Object value1, Object value2) {
        return new Restrictions(Restriction.between, property, value1, value2);
    }

    public static Restrictions eq(String property, Object value) {
        return new Restrictions(Restriction.eq, property, value);
    }

    //	public static Restrictions gt(String property,String value) {
    //		return null;
    //	}
    //	
    //	public static Restrictions lt(String property,String value) {
    //		return null;
    //	}
    //	
    //	public static Criterion le(String property,String value) {
    //		return null;
    //	}
    //	
    //	public static Criterion in(String property,String value) {
    //		return null;
    //	}

    public Restriction getRestriction() {
        return r;
    }

    public Object[] getValues() {
        return values;
    }

    public String getProperty() {
        return property;
    }

}
