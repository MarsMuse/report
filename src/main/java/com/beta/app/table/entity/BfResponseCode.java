package com.beta.app.table.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:  BfResponseCode   
 * @Description:TODO(宝付获取到响应码)   
 * @author: zouyao
 * @date:   2017年6月30日 下午1:52:17   
 *     
 * @Copyright: 2017 
 *
 */
public class BfResponseCode {
    public static  Map<String , String>  CODE_LIB = new HashMap<String , String>();
    static{
        CODE_LIB.put("0000", "交易成功");
        CODE_LIB.put("BF00114", "订单已支付成功，请勿重复支付");
        CODE_LIB.put("BF00100", "系统异常，请联系宝付");
        CODE_LIB.put("BF00112", "系统繁忙，请稍后再试");
        CODE_LIB.put("BF00113", "交易结果未知，请稍后查询");
        CODE_LIB.put("BF00115", "交易处理中，请稍后查询");
        CODE_LIB.put("BF00144", "该交易有风险,订单处理中");
        CODE_LIB.put("BF00202", "交易超时，请稍后查询");
    }
}
