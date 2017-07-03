package com.beta.app.table.entity;

public class BfSingleCharge {
    private  String  trade_date;
    private  String  resp_msg;
    private  String  resp_code;
    private  String loanName;
    private  String loanNo;
    private  String branchOrg;
    private  String chargeChannel = "宝付";
    //状态返回码
    @SuppressWarnings("unused")
    private  String chargeCode;
    
    
    public String getChargeCode() {
        return resp_code;
    }
    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }
    public String getTrade_date() {
        return trade_date;
    }
    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }
    public String getResp_msg() {
        return resp_msg;
    }
    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }
    public String getResp_code() {
        if( resp_code == null || "".equals(resp_code.trim())){
            return "";
        }else{
            String  temp = BfResponseCode.CODE_LIB.get(resp_code.trim()) ;
            temp =  temp == null ? "交易失败":temp;
            return temp;
        }
    }
    public void setResp_code(String resp_code) {
        this.resp_code = resp_code;
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
