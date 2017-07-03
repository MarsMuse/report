package com.beta.app.table.entity;

import java.util.HashMap;
import java.util.Map;

public class ZjSuccessCode {
    public static  Map<String , String>  CODE_LIB = new HashMap<String , String>();
    static{
        CODE_LIB.put("000000", "交易成功");
        CODE_LIB.put("0", "交易成功");
        CODE_LIB.put("20", "扣款中");
        CODE_LIB.put("30", "交易成功");
        CODE_LIB.put("40", "交易失败");
    }
}
