package com.blogadmin.core.constant;

import com.blogadmin.core.utils.response.BaseResult;

public interface CodeBuilder {

    public void build(BaseResult result);

    public void build(BaseResult result, Object... args);

}
