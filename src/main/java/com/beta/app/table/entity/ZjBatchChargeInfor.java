package com.beta.app.table.entity;

public class ZjBatchChargeInfor {
    //合同号
    private  String  itemNo;
    //客户姓名
    private  String  accountName;
    //银行卡号
    private  String  accountNumber;
    //金额
    private  String  amount;
    //状态
    private  String  status;
    //扣款时间
    private  String  bankTxTime;
    //响应码
    private  String  responseCode;
    //扣款结果
    private  String  responseMessage;
    //扣款渠道
    private  String  chargeChannel = "中金";
    //分公司
    private  String  branchOrg;
    //状态返回码
    @SuppressWarnings("unused")
    private  String chargeCode;
    
    
    public String getChargeCode() {
        return status;
    }
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }
    public String getItemNo() {
        String trmpNo;
        
        if(itemNo.indexOf("-")>0){
            trmpNo = itemNo.split("-")[0];
        }else{
            trmpNo = itemNo.substring(0, itemNo.length()-12);
        }
        
        return trmpNo;
    }
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
        
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getStatus() {
        if( status == null || "".equals(status.trim())){
            return "";
        }else{
            String  temp = ZjSuccessCode.CODE_LIB.get(status.trim()) ;
            temp =  temp == null ? "交易失败":temp;
            return temp;
        }
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getBankTxTime() {
        return bankTxTime;
    }
    public void setBankTxTime(String bankTxTime) {
        this.bankTxTime = bankTxTime;
    }
    public String getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public String getChargeChannel() {
        return chargeChannel;
    }
    public void setChargeChannel(String chargeChannel) {
        this.chargeChannel = chargeChannel;
    }
    public String getBranchOrg() {
        return branchOrg;
    }
    public void setBranchOrg(String branchOrg) {
        this.branchOrg = branchOrg;
    }
    
    
}
