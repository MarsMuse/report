package com.beta.app.table.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.beta.app.remote.entity.BatchChargeEntity;
import com.beta.app.remote.entity.SingleChargeEntity;
import com.beta.app.table.entity.AnSingleCharge;
import com.beta.app.table.entity.BfSingleCharge;
import com.beta.app.table.entity.ChargeInfor;
import com.beta.app.table.entity.MenuInfor;
import com.beta.app.table.entity.ZjBatchChargeInfor;
import com.beta.app.table.entity.ZjSingleCharge;

public interface TableDao {

    List<ChargeInfor>  getListForChargeInfor(@Param("parameter")Map<String , String>   parameter  ,  @Param("rowBounds")RowBounds rowBounds);
    
    List<String>   getSingleSynedInfor(@Param("parameter")Map<String , Object>   parameter );
    
    List<String>   getBatchSynedInfor(@Param("parameter")Map<String , Object>   parameter );
    
    void  insertSynBatchInfor(@Param("batchList")List<BatchChargeEntity>  batchList);
    
    void  insertSynSingleInfor(@Param("singleList")List<SingleChargeEntity>   singleList);
    
    List<BatchChargeEntity>  getBatchInforToReslove(@Param("parameter")Map<String , Object>   parameter );
    
    List<SingleChargeEntity>  getSingleInforToReslove(@Param("parameter")Map<String , Object>   parameter );
    
    List<MenuInfor>  getMenuInfor();
    
    void insertBatchResloveData(@Param("zjBatchAllInfor")List<ZjBatchChargeInfor>  zjBatchAllInfor);
    
    void updateBatchResloveFlag(@Param("resloveBatchList")List<BatchChargeEntity>  resloveBatchList);
    
    void  insertZjSingleCharge(@Param("zjSingle")List<ZjSingleCharge>  zjSingle);
    
    void  insertAnSingleCharge(@Param("anSingle")List<AnSingleCharge>  anSingle);
    
    void  insertBfSingleCharge(@Param("bfSingle")List<BfSingleCharge>  bfSingle);
    
    void updateSingleResloveFlag(@Param("resloveSingleList")List<SingleChargeEntity>  resloveSingleList);
}
