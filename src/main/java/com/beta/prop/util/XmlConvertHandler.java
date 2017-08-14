package com.beta.prop.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




/**
 * 
 * @ClassName:  XmlConvertHandler   
 * @Description:(xml与Java对象之间的转换)   
 * @author: zouyao
 * @date:   2017年6月27日 下午1:26:54   
 *     
 * @Copyright: 2017 
 *
 */
public class XmlConvertHandler<T> {

    //日志打印对象
    private  Logger  log  =  LoggerFactory.getLogger(XmlConvertHandler.class);
    
    
    /**
     * 
     * @Title: domConvert   
     * @Description:(将Clob中的XML解析后，存入到Java对象中)   
     * @param: @param entityClass
     * @param: @param xmlClob
     * @param: @param entityTagName
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    public  List<T>  domConvert(Class<T>  entityClass , Clob xmlClob , String entityTagName){
        if(xmlClob == null)
        { 
            return null;
        }
        List<T>  result = null;
        InputStream  input  =  null;
        //将Clob转化为输入流，读取数据
        try {
            input  =  xmlClob.getAsciiStream();
            int length  =  (int) xmlClob.length();
            byte[]  buffer  = new byte[length];
            //读取Clob
            while(length != -1){
                length = input.read(buffer, 0, length);
            }
            
        }catch (SQLException e) {
            log.error("读取Clob流异常");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("读取Clob流出现IO异常");
            e.printStackTrace();
        }
        
        result  =  this.domConvert(entityClass, input, entityTagName);
        return result;
    }
    /**
     * 
     * @Title: domConvert   
     * @Description:(转换字符串中的XML至JavaBean)   
     * @param: @param entityClass
     * @param: @param xmlSource
     * @param: @param entityTagName
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    public  List<T>  domConvert(Class<T>  entityClass , String xmlSource , String entityTagName){
        if(xmlSource == null)
        { 
            return null;
        }
        List<T>  result  = null;
        //编码字符串
        StringReader  reader = new  StringReader(xmlSource);
        //通过流解析成SAX的inputSource
        InputSource input =  new InputSource(reader);
        
        result = this.domConvert(entityClass, input, entityTagName);
        return result;
    }
    
    
    /**
     * 
     * @Title: domConvert   
     * @Description: (转换文件中的Xml)   
     * @param: @param entityClass
     * @param: @param file
     * @param: @param entityTagName
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    public  List<T>  domConvert(Class<T>  entityClass  ,File file , String entityTagName){
        if(file == null)
        { 
            return null;
        }
        List<T> result  = null;
        
        InputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error("文件流异常");
            e.printStackTrace();
        }
        
        result = this.domConvert(entityClass, input, entityTagName);
        
        return result;
    }
    
    
    /**
     * 
     * @Title: domConvert   
     * @Description: (xml转换到对象)   
     * @param: @param entityClass
     * @param: @param input
     * @param: @param entityTagName
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    public  List<T>  domConvert(Class<T>  entityClass  ,  InputStream  input ,  String entityTagName){
        if(input == null)
        { 
            return null;
        }
        log.debug("开始解析Xml");
        List<T>  result =  null;
      //文本对象工厂
        DocumentBuilderFactory  factory  =  DocumentBuilderFactory.newInstance();
      //文本构造器
        DocumentBuilder builder  = null;
      //XML文本对象
        Document  document =  null;
        try {
            builder = factory.newDocumentBuilder();
            document  =  builder.parse(input);
        } catch (ParserConfigurationException e) {
            log.error("构建文本对象出现转换异常");
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("构建文本对象出现SAX异常");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("构建文本对象出现IO异常");
            e.printStackTrace();
        }
        
        
        result = this.splitElementToBind(entityClass, document , entityTagName);
        return result;
    }
    
    /**
     * 
     * @Title: domConvert   
     * @Description: (DOM转换)   
     * @param: @param entityClass
     * @param: @param input
     * @param: @param entityTagName
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    public  List<T>  domConvert(Class<T>  entityClass  ,  InputSource  input ,  String entityTagName){
        if(input == null)
        { 
            return null;
        }
        log.debug("开始解析Xml,创建类 {} " ,entityClass.getName());
        List<T>  result =  null;
        //文本对象工厂
        DocumentBuilderFactory  factory  =  DocumentBuilderFactory.newInstance();
        //文本构造器
        DocumentBuilder builder  = null;
        //XML文本对象
        Document  document =  null;
        //构建XML文本对象
        try {
            builder = factory.newDocumentBuilder();
            document  =  builder.parse(input);
        } catch (ParserConfigurationException e) {
            log.error("构建文本对象出现转换异常");
            e.printStackTrace();
        } catch (SAXException e) {
            log.error("构建文本对象出现SAX异常");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("构建文本对象出现IO异常");
            e.printStackTrace();
        }
        
        result = this.splitElementToBind(entityClass, document , entityTagName);
        return result;
    }
    
    /**
     * 
     * @Title: splitElementToBind   
     * @Description: (将元素拆分并获取值)   
     * @param: @param entityClass
     * @param: @param entityList
     * @param: @return      
     * @return: List<T>      
     * @throws
     */
    private  List<T>  splitElementToBind(Class<T>  entityClass,Document  document , String entityTagName){
        List<T>  result = new ArrayList<T>();
        Element  rootElement = document.getDocumentElement();
        //获取到域数组
        Field[] fields = entityClass.getDeclaredFields();
        //获取到需要转换的对象结点集合
        NodeList  entityList= null;
        if(entityTagName == null || "root".equals(entityTagName)){
            T obj = this.createNewObject(rootElement.getChildNodes(), entityClass, fields);
            result.add(obj);
        }
        else{
            entityList  = rootElement.getElementsByTagName(entityTagName);
        }
        
        if(entityList != null){
            //循环遍历集合，寻求子节点信息
            for(int i = 0 ; i < entityList.getLength() ; i++){
                T obj = null;
              //获取到对象节点
                Node node = entityList.item(i);
                //取出子节点集合
                NodeList  nodeList = node.getChildNodes();
                obj = this.createNewObject(nodeList, entityClass, fields);
                
                result.add(obj);
            }
        }
        
        return result;
        
    }
    
    
    private T   createNewObject(NodeList  nodeList , Class<T>  entityClass ,Field[] fields){
        T  obj = null;
        
        try {
            //反射创建对象
            obj = entityClass.newInstance();
        } catch (InstantiationException e) {
            log.error("反射构建对象出现异常，请检测构造器");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.error("反射构建对象出现异常，请检测构造器");
            e.printStackTrace();
        }
        
        //将子节点与对象绑定
        this.bindNodeListToObject(obj, fields, nodeList);
        
        return obj;
    }
    
    /**
     * 
     * @Title: bindNodeListToObject   
     * @Description: (将XML节点与JavaBean进行绑定)   
     * @param: @param entityClass
     * @param: @return      
     * @return: T      
     * @throws
     */
    private  void  bindNodeListToObject(Object obj,  Field[] fields,  NodeList  nodeList){
        
        //判断是否传入空值，规避异常
        if(obj != null &&  nodeList!=null &&  fields!= null){
            //循环遍历子节点获取节点名称与值
            for(int i = 0 ; i < nodeList.getLength() ; i++){
                
                Node node  = nodeList.item(i);
                String  tagName = node.getNodeName().toLowerCase();
                String value = node.getFirstChild() ==null ? null :node.getFirstChild().getNodeValue();
                //若为空值则无需赋值
                if(value == null){
                    continue;
                }
                //将子节点名称与对象属性名进行循环匹配
                for(int j = 0 ; j < fields.length ; j++){
                    Field field = fields[j];
                    
                    String  fieldName =  field.getName().toLowerCase();
                    //如果XML标签能够匹配上对象的属性名则赋值
                    if(tagName.equals(fieldName)){
                        try {
                            
                            if (field.isAccessible()) {
                                field.set(obj, value);
                                
                            } else {
                                field.setAccessible(true);
                                field.set(obj, value);
                                field.setAccessible(false);
                            }
                        } catch (IllegalArgumentException e) {
                            log.error("反射赋值出现异常，请检测GETTER SETTER");
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            log.error("反射赋值出现异常，请检测GETTER SETTER");
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    
}
