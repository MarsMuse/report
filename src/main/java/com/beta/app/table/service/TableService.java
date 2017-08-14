package com.beta.app.table.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.beta.app.remote.dao.RemoteDao;
import com.beta.app.remote.entity.BatchChargeEntity;
import com.beta.app.remote.entity.SingleChargeEntity;
import com.beta.app.table.dao.TableDao;
import com.beta.app.table.entity.AnSingleCharge;
import com.beta.app.table.entity.BfSingleCharge;
import com.beta.app.table.entity.ChargeInfor;
import com.beta.app.table.entity.LoanNoAndOrg;
import com.beta.app.table.entity.MenuInfor;
import com.beta.app.table.entity.SynDataMessage;
import com.beta.app.table.entity.ZjBatchChargeInfor;
import com.beta.app.table.entity.ZjSingleCharge;
import com.beta.prop.pagination.BindedRowBounds;
import com.beta.prop.pagination.Page;
import com.beta.prop.util.XmlConvertHandler;

@Service
public class TableService {
    
    
    private Logger  log  =  LoggerFactory.getLogger(TableService.class);
    
    @Resource
    private TableDao tableDao;
    
    @Resource
    private  RemoteDao  remoteDao;
    
    public Map<String , Object>  getListForChargeInfor(Map<String , String>   paramInfor  , Page page){
         Map<String , Object> result=  new HashMap<String , Object>();
         if(page != null){
             BindedRowBounds rowBounds= new BindedRowBounds(page);
             List<ChargeInfor>  data = tableDao.getListForChargeInfor(paramInfor , rowBounds );
             result.put("result", data);
             result.put("totalCount", rowBounds.getPage().getTotalRecords());
             
         }
         else{
            List<ChargeInfor>  data = tableDao.getListForChargeInfor(paramInfor , RowBounds.DEFAULT );
            result.put("result", data);
         }
         
        return result;
    }
    
    /**
     * 
     * @Title: synChargeData   
     * @Description:(同步数据，只部署单机直接用同步锁)   
     * @param: @param parameter
     * @param: @return      
     * @return: SynDataMessage      
     * @throws
     */
    public synchronized SynDataMessage   synChargeData(Map<String , Object>  parameter){
        SynDataMessage  sdm = new SynDataMessage();
        log.info("开始同步数据");
        String  synDate =  (String) parameter.get("synDate");
        if(synDate == null || "".equals(synDate)){
            SimpleDateFormat  sdf =  new SimpleDateFormat("yyyy-MM-dd");
            
            synDate = sdf.format(new Date()).toString();
            parameter.put("synDate", synDate);
        }
        //获取到已同步的单扣数据
        List<String>  synedSingleCharge = this.tableDao.getSingleSynedInfor(parameter);
        if(synedSingleCharge != null && synedSingleCharge.size() !=0){
            parameter.put("exSingleList", synedSingleCharge);
        }
        //获取到已同步的批扣数据
        List<String>  synedBatchCharge = this.tableDao.getBatchSynedInfor(parameter);
        if(synedBatchCharge != null && synedBatchCharge.size() !=0){
            parameter.put("exBatchList", synedBatchCharge);
        }
        
      //获取到需要同步的单扣数据
        List<SingleChargeEntity>  synSingleCharge  =  this.remoteDao.getSingleInforToSyn(parameter);
        //获取到需要同步的批扣数据
        List<BatchChargeEntity>  synBatchCharge  =  this.remoteDao.getBatchInforToSyn(parameter);
        if(synSingleCharge!= null && synSingleCharge.size() !=0){
            List<List<SingleChargeEntity>>  array = splitList(synSingleCharge );
            if(array != null && array.size() >0){
                for(List<SingleChargeEntity> temp :array ){
                    this.tableDao.insertSynSingleInfor(temp);
                }
            }
        }
        if(synBatchCharge!= null && synBatchCharge.size() !=0){
            this.tableDao.insertSynBatchInfor(synBatchCharge);
        }
        sdm.setSingleChargeCount(synSingleCharge.size());
        sdm.setBatchChargeCount(synBatchCharge.size());
        sdm.setSynDate(synDate);
        return sdm;
    }
    
    /**
     * 
     * @Title: resloveChargeInfo   
     * @Description:(解析划扣数据)   
     * @param: @param parameter
     * @param: @return      
     * @return: SynDataMessage      
     * @throws
     */
    @Transactional
    public  SynDataMessage  resloveChargeInfo(Map<String , Object>  parameter){
        
        SynDataMessage  sdm = new SynDataMessage();
        log.info("开始解析数据");
        String  synDate =  (String) parameter.get("synDate");
        if(synDate == null || "".equals(synDate)){
            SimpleDateFormat  sdf =  new SimpleDateFormat("yyyy-MM-dd");
            
            synDate = sdf.format(new Date()).toString();
            parameter.put("synDate", synDate);
        }
        //获取到需要解析的批扣数据集合
        List<BatchChargeEntity>  resloveBatchList = this.tableDao.getBatchInforToReslove(parameter);
        //获取到需要解析的单扣数据
        List<SingleChargeEntity>  resloveSingleList =  this.tableDao.getSingleInforToReslove(parameter);
        //总的扣款信息
        List<ZjBatchChargeInfor>  zjBatchAllInfor = new ArrayList<ZjBatchChargeInfor>();
        if(resloveBatchList != null && resloveBatchList.size() >0){
            XmlConvertHandler<ZjBatchChargeInfor>  xchByZjBatch  = new XmlConvertHandler<ZjBatchChargeInfor>();
            for(int i = 0 ; i<resloveBatchList.size() ; i++){
                BatchChargeEntity bce = resloveBatchList.get(i);
                List<ZjBatchChargeInfor>  result  =  xchByZjBatch.domConvert(ZjBatchChargeInfor.class, bce.getInterfaceContent(), "Item");
                zjBatchAllInfor.addAll(result);
            }
        }
        Set<String>  loanSet = new HashSet<String>();
        //排除冗余的loanNo
        for(ZjBatchChargeInfor zbc:zjBatchAllInfor){
            loanSet.add(zbc.getItemNo());
        }
        //转化成LIST
        List<String>  loanList = new ArrayList<String>(loanSet);
        //获取到合同号对应的分公司
        List<LoanNoAndOrg>  orgInforList = null;
        if(loanList != null && loanList.size()>0){
            orgInforList = this.remoteDao.getLoanNoAndOrg(loanList);
        }
        
        
        
        
        //获取到对应分公司信息
        if(zjBatchAllInfor!= null && zjBatchAllInfor.size()>0 && orgInforList != null && orgInforList.size()>0){
            for(ZjBatchChargeInfor  zbc : zjBatchAllInfor){
                
                for(LoanNoAndOrg org : orgInforList){
                    if(org.getLoanNo().equals(zbc.getItemNo())){
                        zbc.setBranchOrg(org.getBranchOrg());
                        break;
                    }
                }
            }
        }
        //标志位为1
        List<ZjSingleCharge>  zjSingle = new ArrayList<ZjSingleCharge>();
        XmlConvertHandler<ZjSingleCharge>  zjXch  = new XmlConvertHandler<ZjSingleCharge>();
        //标志位2
        List<AnSingleCharge>  anSingle = new ArrayList<AnSingleCharge>();
        //标志位3
        List<BfSingleCharge>  bfSingle = new ArrayList<BfSingleCharge>();
        XmlConvertHandler<BfSingleCharge>  bfXch  = new XmlConvertHandler<BfSingleCharge>();
        
        for(SingleChargeEntity sce : resloveSingleList){
            if("1".equals(sce.getStatus().trim())){
                List<ZjSingleCharge>  zjResult =  zjXch.domConvert(ZjSingleCharge.class, sce.getInterfaceContent(), "Body");
                if(zjResult != null && zjResult.size()>0){
                    for(ZjSingleCharge sc: zjResult){
                        sc.setLoanNo(sce.getLoanContractNo());
                        sc.setLoanName(sce.getLoanName());
                        sc.setBranchOrg(sce.getBranchOrg());
                    }
                    zjSingle.addAll(zjResult);
                }
            }else if("2".equals(sce.getStatus().trim())){
                AnSingleCharge anJson = JSON.parseObject(sce.getInterfaceContent(), AnSingleCharge.class);
                anJson.setLoanNo(sce.getLoanContractNo());
                anJson.setLoanName(sce.getLoanName());
                anJson.setBranchOrg(sce.getBranchOrg());
                anSingle.add(anJson);
            }else if("3".equals(sce.getStatus().trim())){
                List<BfSingleCharge>  bfResult =  bfXch.domConvert(BfSingleCharge.class, sce.getInterfaceContent(), "root");
                if(bfResult != null && bfResult.size()>0){
                    for(BfSingleCharge sc: bfResult){
                        sc.setLoanNo(sce.getLoanContractNo());
                        sc.setLoanName(sce.getLoanName());
                        sc.setBranchOrg(sce.getBranchOrg());
                    }
                    bfSingle.addAll(bfResult);
                }
            }
        }
        
        if(zjBatchAllInfor.size()>0 || resloveBatchList.size()>0){
            this.updateBatchChargeInfor(zjBatchAllInfor, resloveBatchList);
        }
        
        if(resloveSingleList.size()>0 || zjSingle.size()>0 || anSingle.size()>0 ||bfSingle.size()>0){
            
            this.updateSingleChargeInfor(resloveSingleList, zjSingle, anSingle, bfSingle);
        }
        sdm.setBatchChargeCount(zjBatchAllInfor.size());
        sdm.setSingleChargeCount(zjSingle.size()+anSingle.size()+bfSingle.size());
        sdm.setSynDate(synDate);
        return sdm;
    }
    
    
    public  void  updateBatchChargeInfor(List<ZjBatchChargeInfor>  zjBatchAllInfor ,List<BatchChargeEntity>  resloveBatchList){
        if(zjBatchAllInfor != null && zjBatchAllInfor.size()>0){
            List<List<ZjBatchChargeInfor>>  all = splitList(zjBatchAllInfor);
            if(all != null && all.size()>0){
                for(List<ZjBatchChargeInfor> temp : all)
                 this.tableDao.insertBatchResloveData(temp);
            }
        }
        if(resloveBatchList!=null && resloveBatchList.size()>0){
            
            List<List<BatchChargeEntity>>  all = splitList(resloveBatchList);
            if(all != null && all.size()>0){
                for(List<BatchChargeEntity> temp : all){
                    this.tableDao.updateBatchResloveFlag(temp);
                }
            }
        }
    }
    
    public void updateSingleChargeInfor(List<SingleChargeEntity>  resloveSingleList , List<ZjSingleCharge>  zjSingle ,List<AnSingleCharge>  anSingle  ,List<BfSingleCharge>  bfSingle){
        if(zjSingle != null && zjSingle.size()>0){
        	List<List<ZjSingleCharge>>  all = splitList(zjSingle);
            if(all != null && all.size()>0){
                for(List<ZjSingleCharge> temp : all){
                    this.tableDao.insertZjSingleCharge(temp);
                }
            }
        }
        
        if(anSingle != null && anSingle.size()>0){
        	
        	List<List<AnSingleCharge>>  all = splitList(anSingle);
            if(all != null && all.size()>0){
                for(List<AnSingleCharge> temp : all){
                    this.tableDao.insertAnSingleCharge(temp);
                }
            }
        }
        
        if(bfSingle != null && bfSingle.size()>0){
            List<List<BfSingleCharge>>  all = splitList(bfSingle);
            if(all != null && all.size()>0){
                for(List<BfSingleCharge> temp : all){
                    this.tableDao.insertBfSingleCharge(temp);
                }
            }
        }
        
        if(resloveSingleList != null && resloveSingleList.size()>0){
        	List<List<SingleChargeEntity>>  all = splitList(resloveSingleList);
            if(all != null && all.size()>0){
                for(List<SingleChargeEntity> temp : all){
                    this.tableDao.updateSingleResloveFlag(temp);
                }
            }
        }
    }
    
    
    public List<MenuInfor>  getMenuInfor(){
        List<MenuInfor> result = null;
        result = this.tableDao.getMenuInfor();
        
        return result;
    }
    
    
   public  <T> List<List<T>>  splitList(List<T>  source){
       List<List<T>> result = null;
       if(source == null || source.isEmpty()){
           return result;
       }
       result = new ArrayList<>();
       int loopCount = (source.size()-1)/2000 +1;
       for(int i = 1 ; i <=loopCount ;i++){
           List<T>  temp = null;
           
           if(i !=loopCount){
               temp = source.subList((i-1)*2000, 2000*i);
           }else{
               temp = source.subList((i-1)*2000, source.size());
           }
           if( temp!= null &&  temp.size()>0){
               result.add(temp);
           }
       }
       return result;
        
    }
    
}
