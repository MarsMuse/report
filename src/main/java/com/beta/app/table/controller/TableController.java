package com.beta.app.table.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beta.app.table.entity.ChargeInfor;
import com.beta.app.table.entity.MenuInfor;
import com.beta.app.table.entity.SynDataMessage;
import com.beta.app.table.service.TableService;
import com.beta.app.utils.ExportExcelByPOI;
import com.beta.prop.pagination.Page;

/**
 * 
 * <p>application name:{应用名称}</p>
 * <p>application describing:{功能描述}</p
 * <p>company:neusoft</p>
 * <p>time:2017年6月4日</p>
 * @author {FireMonkey}
 * @version {1.0}
 */

@Controller
@RequestMapping(value="/charge")
public class TableController {
    
    
    @Resource
    private TableService tableService;
    
    @RequestMapping(value="/info")
    @ResponseBody
    public Map<String , Object>  getListForChargeInfor(HttpServletRequest request  , HttpServletResponse response ){

        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        Page page = new Page(currentPage, limit);
        Map<String , String>  parameter = new HashMap<String , String>();  
        String synDate = request.getParameter("synDate");
        if(synDate != null && !("".equals(synDate))){
            parameter.put("synDate", synDate.replaceAll("-", ""));
        }else{
            SimpleDateFormat  sdf =  new SimpleDateFormat("yyyyMMdd");
            synDate = sdf.format(new Date()).toString();
            parameter.put("synDate", synDate);
        }
        return tableService.getListForChargeInfor(parameter , page);
    }
    
    @RequestMapping(value="/syn")
    @ResponseBody
    public SynDataMessage synChargeData(HttpServletRequest request){
        Map<String , Object>  parameter = new HashMap<String , Object>();  
        String synDate = request.getParameter("synDate");
        if(synDate != null && !("".equals(synDate))){
            parameter.put("synDate", synDate);
        }
        SynDataMessage   result = null;
        result = this.tableService.synChargeData(parameter);
        
        return result;
    }
    
    
    @RequestMapping(value="/reslove")
    @ResponseBody
    public SynDataMessage resloveChargeData(HttpServletRequest request){
        Map<String , Object>  parameter = new HashMap<String , Object>();  
        String synDate = request.getParameter("synDate");
        if(synDate != null && !("".equals(synDate))){
            parameter.put("synDate", synDate);
        }
        SynDataMessage   result = null;
        result = this.tableService.resloveChargeInfo(parameter);
        
        return result;
    }
    
    @RequestMapping(value="/export")
    public void exportData(HttpServletRequest request ,HttpServletResponse  response ){
        Map<String , String>  parameter = new HashMap<String , String>();  
        String synDate = request.getParameter("synDate");
        if(synDate != null && !("".equals(synDate))){
            parameter.put("synDate", synDate.replaceAll("-", ""));
        }else{
            SimpleDateFormat  sdf =  new SimpleDateFormat("yyyyMMdd");
            synDate = sdf.format(new Date()).toString();
            parameter.put("synDate", synDate);
        }
        
        Map<String , Object>  dataList = this.tableService.getListForChargeInfor(parameter, null);
        @SuppressWarnings("unchecked")
        List<ChargeInfor>  data =  (List<ChargeInfor>) dataList.get("result");
      //需要的数据的某个字段
        String[] titleColumn = {"loanNo","loanName","chargeDate","chargeResult","chargeLoseReason","branchOrg","chargeChannel"};
        //需要数据的列名
        String[] headers = {"合同编号","客户姓名","划扣时间","划扣结果","划扣返回信息","分公司","扣款渠道"};
        int[]  widthList = {28 , 20, 20, 24, 36, 24, 12};
        ExportExcelByPOI export   =   new ExportExcelByPOI(response, synDate+"扣款情况"+System.currentTimeMillis(), "扣款信息");
        
        //导出数据
        export.wirteExcel(titleColumn, headers,widthList, data);
        
    }
    
    @RequestMapping(value="/menuinfor")
    @ResponseBody
    public List<MenuInfor>  getMenuInfor(){
        List<MenuInfor> result = null;
        result = this.tableService.getMenuInfor();
        
        return result;
        
    }
}
