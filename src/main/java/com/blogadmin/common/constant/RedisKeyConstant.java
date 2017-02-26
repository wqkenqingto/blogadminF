/*
 * Copyright 2016 gaoshou360.com All right reserved. This software is the
 * confidential and proprietary information of gaoshou360.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with gaoshou360.com .
 */
package com.blogadmin.common.constant;


public class RedisKeyConstant {

    public static String SMS_KEY = "centre:sms";

    public static String getRealKey(String key) {
        return SMS_KEY + ":" + key;
    }

}
