package com.blogadmin.core.utils;

import com.blogadmin.core.utils.response.BaseResult;


public class CodeBuildHelper {

    public static void build(String code, String msg, BaseResult result) {
        result.setErrorMessage(code, msg);
    }

    public static void build(String code, String msg, BaseResult result, Object... args) {
        result.setSuccess(false);
        result.setErrorMessage(code, msg);
        try {
            result.setMessage(String.format(result.getMessage(), args));
        } catch (Exception e) {
        }
    }

}
