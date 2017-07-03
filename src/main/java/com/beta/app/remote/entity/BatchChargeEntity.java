package com.beta.app.remote.entity;

import java.sql.Date;

public class BatchChargeEntity {
    private  String  priNumber;
    
    private  String  requestNo;
    
    private  String  batchNo;
    
    private String interfaceContent;
    
    private  Date createDate;
    
    private String createName;

    public String getPriNumber() {
        return priNumber;
    }

    public void setPriNumber(String priNumber) {
        this.priNumber = priNumber;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
    
    
}
