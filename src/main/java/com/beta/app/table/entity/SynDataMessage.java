package com.beta.app.table.entity;

/**
 * 
 * @ClassName:  SynDataMessage   
 * @Description:TODO(同步信息)   
 * @author: zouyao
 * @date:   2017年6月29日 下午3:39:47   
 *     
 * @Copyright: 2017 
 *
 */
public class SynDataMessage {

    private  long  useTime;
    
    private  int  singleChargeCount;
    
    private  int  batchChargeCount;
    
    private  String  synDate;



    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public int getSingleChargeCount() {
        return singleChargeCount;
    }

    public void setSingleChargeCount(int singleChargeCount) {
        this.singleChargeCount = singleChargeCount;
    }

    public int getBatchChargeCount() {
        return batchChargeCount;
    }

    public void setBatchChargeCount(int batchChargeCount) {
        this.batchChargeCount = batchChargeCount;
    }

    public String getSynDate() {
        return synDate;
    }

    public void setSynDate(String synDate) {
        this.synDate = synDate;
    }
    
    
}
