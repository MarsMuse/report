package com.beta.app.remote.entity;

import java.sql.Date;

public class SingleChargeEntity {

    
    private  String  priNumber;
    
    private String loanContractNo;
    
    private  String loanName;
    
    private String seq;
    
    private  String  interfaceContent;
    
    private  Date  createDate;
    
    private  String createName;
    
    private  String  status;
    
    private   String  branchOrg;

    public String getPriNumber() {
        return priNumber;
    }

    public void setPriNumber(String priNumber) {
        this.priNumber = priNumber;
    }

    public String getLoanContractNo() {
        return loanContractNo;
    }

    public void setLoanContractNo(String loanContractNo) {
        this.loanContractNo = loanContractNo;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getInterfaceContent() {
        return interfaceContent;
    }

    public void setInterfaceContent(String interfaceContent) {
        this.interfaceContent = interfaceContent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBranchOrg() {
        return branchOrg;
    }

    public void setBranchOrg(String branchOrg) {
        this.branchOrg = branchOrg;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }
    
    
}
