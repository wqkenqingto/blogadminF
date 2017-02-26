package com.blogadmin.core.constant;

import com.blogadmin.core.utils.CodeBuildHelper;
import com.blogadmin.core.utils.response.BaseResult;

public enum ResultBack implements CodeBuilder {
    PARAMER_ILLEGLE("illegle param, %s must not null", "1000"),
    USER_LOGIN_INCORRECT("the user %s with password %s is incorrect", "1001"),
    DATA_NOT_EXISTS("the query data %s not exists", "1002"),
    OPERATE_EXCEPTION("there is some unknown exception occurred ", "1003"),
    PARAMER_LENGTH_ILLEGLE("illegle param length", "1004"),
    CUSTOM_MESSAGE("failure", "2000");

    private String msg;
    private String code;

    ResultBack(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public void build(BaseResult result) {
        CodeBuildHelper.build(this.code, this.msg, result);
    }

    @Override
    public void build(BaseResult result, Object... args) {
        CodeBuildHelper.build(this.code, this.msg, result, args);
    }

    public void build(BaseResult result, String code) {
        CodeBuildHelper.build(code, this.msg, result);
    }

    public void build(BaseResult result, String code, Object... args) {
        CodeBuildHelper.build(code, this.msg, result, args);
    }

    public void build(BaseResult result, String code, String msg) {
        CodeBuildHelper.build(code, msg, result);
    }

    public void build(BaseResult result, String code, String msg, Object... args) {
        CodeBuildHelper.build(code, msg, result, args);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
