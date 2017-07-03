package com.beta.app.table.entity;

/**
 * 
 * @ClassName:  ZjSingleCharge   
 * @Description:(中金单扣数据)   
 * @author: zouyao
 * @date:   2017年6月30日 下午1:33:41   
 *     
 * @Copyright: 2017 
 *
 */
public class ZjSingleCharge {
    private  String bankTxTime;
    private  String amount;
    private  String status;
    private  String responseCode;
    private  String responseMessage;
    private  String loanName;
    private  String loanNo;
    private  String branchOrg;
    private  String chargeChannel = "中金";
    
    //状态返回码
    @SuppressWarnings("unused")
    private  String chargeCode;
    
    
    public String getChargeCode() {
        return status;
    }
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
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
    public String getChargeChannel() {
        return chargeChannel;
    }
    public void setChargeChannel(String chargeChannel) {
        this.chargeChannel = chargeChannel;
    }
    public String getBankTxTime() {
        return bankTxTime;
    }
    public void setBankTxTime(String bankTxTime) {
        this.bankTxTime = bankTxTime;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
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
    public String getLoanName() {
        return loanName;
    }
    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }
    public String getLoanNo() {
        return loanNo;
    }
    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo;
    }
    public String getBranchOrg() {
        return branchOrg;
    }
    public void setBranchOrg(String branchOrg) {
        this.branchOrg = branchOrg;
    }
    
    
}
