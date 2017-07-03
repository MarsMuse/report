package com.beta.app.remote.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.beta.app.remote.entity.BatchChargeEntity;
import com.beta.app.remote.entity.SingleChargeEntity;
import com.beta.app.table.entity.LoanNoAndOrg;

public interface RemoteDao {
    
    /**
     * 
     * @Title: getBatchInfor   
     * @Description: TODO(获取到需要同步的批扣数据)   
     * @param: @param parameter
     * @param: @return      
     * @return: List<BatchChargeEntity>      
     * @throws
     */
    List<BatchChargeEntity>   getBatchInforToSyn(@Param("parameter")Map<String , Object>  parameter );
    
    /**
     * 
     * @Title: getSynSingleInfor   
     * @Description: TODO(获取到需要同步的单扣数据)   
     * @param: @param parameter
     * @param: @return      
     * @return: List<SingleChargeEntity>      
     * @throws
     */
    List<SingleChargeEntity>  getSingleInforToSyn(@Param("parameter")Map<String , Object>  parameter );
    
    List<String>  getLoanOrgIinfor(@Param("loanNo")String loanNo);
    
    List<LoanNoAndOrg>  getLoanNoAndOrg(@Param("loanNoList")List<String>  loanNoList);
}
