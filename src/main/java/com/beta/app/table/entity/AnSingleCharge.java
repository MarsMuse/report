package com.beta.app.table.entity;

/**
 * 
 * @ClassName:  AnSingleCharge   
 * @Description:TODO(爱农单扣数据)   
 * @author: zouyao
 * @date:   2017年6月30日 下午1:33:59   
 *     
 * @Copyright: 2017 
 *
 */
public class AnSingleCharge {
    private  String  txnType;
    private  String  respCode;
    private  String  canRefAmt;
    private  String  succTime;
    private  String  txnTime;
    private  String  respMsg;
    private  String loanName;
    private  String loanNo;
    private  String branchOrg;
    private  String chargeChannel = "爱农";
    //状态返回码
    @SuppressWarnings("unused")
    private  String chargeCode;
    
    
    public String getChargeCode() {
        return respCode;
    }
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }
    public String getTxnType() {
        return txnType;
    }
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    public String getRespCode() {
        if( respCode == null || "".equals(respCode.trim())){
            return "";
        }else{
            String  temp = AnSuccessCode.CODE_LIB.get(respCode.trim()) ;
            temp =  temp == null ? "交易失败":temp;
            return temp;
        }
    }
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    public String getCanRefAmt() {
        return canRefAmt;
    }
    public void setCanRefAmt(String canRefAmt) {
        this.canRefAmt = canRefAmt;
    }
    public String getSuccTime() {
        return succTime;
    }
    public void setSuccTime(String succTime) {
        this.succTime = succTime;
    }
    public String getTxnTime() {
        return txnTime;
    }
    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
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
    public String getChargeChannel() {
        return chargeChannel;
    }
    public void setChargeChannel(String chargeChannel) {
        this.chargeChannel = chargeChannel;
    }
    
    
}
