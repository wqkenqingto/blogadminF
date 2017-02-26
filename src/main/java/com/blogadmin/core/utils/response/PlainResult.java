package com.blogadmin.core.utils.response;


public class PlainResult<T> extends BaseResult {

    /**
     * 
     */
    private static final long serialVersionUID = 7083406773571014850L;
    private T                 data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
