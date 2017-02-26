package com.blogadmin.common.product;

import com.blogadmin.wh.model.Unit;

/**
 * Created by wqkenqin on 2016/11/9.
 * 用于创建相关实体
 */
public class EntityProduct {
        static  Unit unit;
      static   String  ucodeR="mu";
           /*用于创建Unit*/
    public static Unit productUnit(String uname,String cid){
        unit=new Unit();
        unit.setUname(uname);
        String ucode=ucodeR+"000007";
        unit.setCid(cid);
        unit.setUcode(ucode);
        return unit;
    }

}
