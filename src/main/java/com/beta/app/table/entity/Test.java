package com.beta.app.table.entity;

import com.alibaba.fastjson.JSON;

public class Test {
public static void main(String[] args) {
	String source ="{\"txnType\":\"11\",\"respCode\":\"1002\",\"canRefAmt\":\"0000000000318649\",\"refCnt\":\"0\",\"refAmt\":\"0000000000000000\",\"channelId\":\"chinaGpay\",\"merId\":\"929000000000088\",\"settleDate\":\"\",\"txnSubType\":\"01\",\"version\":\"1.0.0\",\"txnAmt\":\"0000000000318649\",\"currency\":\"CNY\",\"settleCurrency\":\"\",\"signMethod\":\"MD5\",\"settleAmount\":\"\",\"respMsg\":\"交易失败\",\"resv\":\"\",\"bizType\":\"000501\",\"merResv1\":\"\",\"merOrderId\":\"CC201611280323and1498568999587\",\"signature\":\"sJu8vts/qWgYQSbjk7X35Q==\",\"succTime\":\"20170627230716\",\"accessType\":\"0\",\"txnTime\":\"20170627210959\"}";
	AnSingleCharge a =  JSON.parseObject(source, AnSingleCharge.class);
	System.out.println(a.getTxnTime());
	System.out.println(a.getSuccTime());
	System.out.println(a.getRespCode());
}
}
