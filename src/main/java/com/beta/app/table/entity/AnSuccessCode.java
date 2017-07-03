package com.beta.app.table.entity;

import java.util.HashMap;
import java.util.Map;

public class AnSuccessCode {
    public static  Map<String , String>  CODE_LIB = new HashMap<String , String>();
    static{
        CODE_LIB.put("1111", "扣款中");
        CODE_LIB.put("1001", "交易成功");
        CODE_LIB.put("1002", "交易失败");
    }
}
