package com.beta.app.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ExportExcelByPOI   
 * @Description:TODO(POI导出Excel文档)   
 * @author: zouyao
 * @date:   2017年5月9日 下午5:54:03   
 *     
 * @Copyright: 2017 
 *
 */
public class ExportExcelByPOI {
    /**
     * 日志打印对象
     */
    private  Logger log =  LoggerFactory.getLogger(ExportExcelByPOI.class);
    HttpServletResponse response;
    // 文件名
    private String fileName;
    // 文件保存路径
    private String fileDir;
    // sheet名
    private String sheetName;
    // 表头字体
    private String titleFontType = "Arial Unicode MS";
    // 表头背景色
    private String titleBackColor = "C1FBEE";
    // 表头字号
    private short titleFontSize = 12;
    // 添加自动筛选的列 如 A:M
    private String address = "";
    // 正文字体
    private String contentFontType = "Arial Unicode MS";
    // 正文字号
    private short contentFontSize = 12;
    // Float类型数据小数位
    private String floatDecimal = ".00";
    // Double类型数据小数位
    private String doubleDecimal = ".00";
    // 设置列的公式
    private String colFormula[] = null;
    DecimalFormat floatDecimalFormat = new DecimalFormat(floatDecimal);
    DecimalFormat doubleDecimalFormat = new DecimalFormat(doubleDecimal);
    private HSSFWorkbook workbook = null;

    /**
     * 
     * @Title:  ExportExcelByPOI   
     * @Description:    TODO(构造器1)   
     * @param:  @param fileDir
     * @param:  @param sheetName  
     * @throws
     */
    public ExportExcelByPOI(String fileDir, String sheetName) {
        this.fileDir = fileDir;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }
    /**
     * 
     * @Title:  ExportExcelByPOI   
     * @Description:    TODO(构造器2)   
     * @param:  @param response
     * @param:  @param fileName
     * @param:  @param sheetName  
     * @throws
     */
    public ExportExcelByPOI(HttpServletResponse response, String fileName, String sheetName) {
        this.response = response;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
        this.fileName = fileName;
    }

    /**
     * 
     * @Title: setTitleFontType   
     * @Description: TODO(设置表头字体)   
     * @param: @param titleFontType      
     * @return: void      
     * @throws
     */
    public void setTitleFontType(String titleFontType) {
        this.titleFontType = titleFontType;
    }

    /**
     * 
     * @Title: setTitleBackColor   
     * @Description: TODO(设置表头字体颜色)   
     * @param: @param titleBackColor      
     * @return: void      
     * @throws
     */
    public void setTitleBackColor(String titleBackColor) {
        this.titleBackColor = titleBackColor;
    }

    /**
     * 
     * @Title: setTitleFontSize   
     * @Description: TODO(设置表头字体大小)   
     * @param: @param titleFontSize      
     * @return: void      
     * @throws
     */
    public void setTitleFontSize(short titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    /**
     * 
     * @Title: setAddress   
     * @Description: TODO(设置自动选择栏目)   
     * @param: @param address      
     * @return: void      
     * @throws
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @Title: setContentFontType   
     * @Description: TODO(设置正文字体)   
     * @param: @param contentFontType      
     * @return: void      
     * @throws
     */
    public void setContentFontType(String contentFontType) {
        this.contentFontType = contentFontType;
    }

    /**
     * 
     * @Title: setContentFontSize   
     * @Description: TODO(设置正文字号)   
     * @param: @param contentFontSize      
     * @return: void      
     * @throws
     */
    public void setContentFontSize(short contentFontSize) {
        this.contentFontSize = contentFontSize;
    }


    /**
     * 
     * @Title: setDoubleDecimal   
     * @Description: TODO(设置float默认两个小数)   
     * @param: @param doubleDecimal      
     * @return: void      
     * @throws
     */
    public void setDoubleDecimal(String doubleDecimal) {
        this.doubleDecimal = doubleDecimal;
    }

    /**
     * 
     * @Title: setFloatDecimalFormat   
     * @Description: TODO(设置double类型默认两位小数)   
     * @param: @param floatDecimalFormat      
     * @return: void      
     * @throws
     */
    public void setFloatDecimalFormat(DecimalFormat floatDecimalFormat) {
        this.floatDecimalFormat = floatDecimalFormat;
    }

    /**
     * 
     * @Title: setColFormula   
     * @Description: TODO(设置列公式)   
     * @param: @param colFormula      
     * @return: void      
     * @throws
     */
    public void setColFormula(String[] colFormula) {
        this.colFormula = colFormula;
    }

    /**
     * 
     * @Title: wirteExcel   
     * @Description: TODO(写Excel)   
     * @param: @param titleColumn
     * @param: @param titleName
     * @param: @param widthList
     * @param: @param dataList      
     * @return: void      
     * @throws
     */
    public void wirteExcel(String titleColumn[], String titleName[], int[] widthList, List<?> dataList) {
        // 添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        Sheet sheet = workbook.createSheet(this.sheetName);
        //日期处理
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //日期样式
        HSSFCellStyle dateCellStyle=workbook.createCellStyle(); 
        //设置显示类型
        short df=workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss");
        //样式设置
        dateCellStyle.setDataFormat(df);
        // 新建文件
        OutputStream out = null;
        try {
            if (fileDir != null) {
                // 有文件路径
                out = new FileOutputStream(fileDir);
            } else {
                // 否则，直接写到输出流中
                out = response.getOutputStream();
                fileName = fileName + ".xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            }
            // 写入excel的表头
            Row titleNameRow = workbook.getSheet(sheetName).createRow(0);
            // 设置样式
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short) 10);
            for (int i = 0; i < titleName.length; i++) {
                sheet.setColumnWidth(i, widthList[i] * 256); // 设置宽度
                Cell cell = titleNameRow.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }
            // 为表头添加自动筛选
            if (!"".equals(address)) {
                CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }
            // 通过反射获取数据并写入到excel中
            if (dataList != null && dataList.size() > 0) {
                // 设置样式
                // HSSFCellStyle dataStyle = workbook.createCellStyle();
                titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
                if (titleColumn.length > 0) {
                    for (int rowIndex = 1; rowIndex <= dataList.size(); rowIndex++) {
                        Object obj = dataList.get(rowIndex - 1); // 获得该对象
                        @SuppressWarnings("rawtypes")
                        Class clsss = obj.getClass(); // 获得该对对象的class实例
                        Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex);
                        for (int columnIndex = 0; columnIndex < titleColumn.length; columnIndex++) {
                            String title = titleColumn[columnIndex].toString().trim();
                            if (!"".equals(title)) { // 字段不为空
                                // 使首字母大写
                                String UTitle = Character.toUpperCase(title.charAt(0))
                                        + title.substring(1, title.length()); // 使其首字母大写;
                                String methodName = "get" + UTitle;
                                // 设置要执行的方法
                                @SuppressWarnings("unchecked")
                                Method method = clsss.getDeclaredMethod(methodName);
                                // 获取返回类型
                                String returnType = method.getReturnType().getName();
                                String data = method.invoke(obj) == null ? "" : method.invoke(obj).toString();
                                Cell cell = dataRow.createCell(columnIndex);
                                if (data != null && !"".equals(data)) {
                                    if ("int".equals(returnType)) {
                                        cell.setCellValue(Integer.parseInt(data));
                                    } else if ("long".equals(returnType)) {
                                        cell.setCellValue(Long.parseLong(data));
                                    } else if ("float".equals(returnType)) {
                                        cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                                    } else if ("double".equals(returnType)) {
                                        cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                                    } else if ("java.util.Date".equals(returnType)) {
                                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                                        cell.setCellValue(sdf.parse(data));
                                        cell.setCellStyle(dateCellStyle);
                                    } else {
                                        cell.setCellValue(data);
                                    }
                                }
                            } else { // 字段为空 检查该列是否是公式
                                if (colFormula != null) {
                                    String sixBuf = colFormula[columnIndex].replace("@", (rowIndex + 1) + "");
                                    Cell cell = dataRow.createCell(columnIndex);
                                    cell.setCellFormula(sixBuf.toString());
                                }
                            }
                        }
                    }
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @Title: setColor   
     * @Description: TODO(设置颜色)   
     * @param: @param style
     * @param: @param color
     * @param: @param index
     * @param: @return      
     * @return: CellStyle      
     * @throws
     */
    public CellStyle setColor(CellStyle style, String color, short index) {
        if (color != "" && color != null) {
            // 转为RGB码
            int r = Integer.parseInt((color.substring(0, 2)), 16); // 转为16进制
            int g = Integer.parseInt((color.substring(2, 4)), 16);
            int b = Integer.parseInt((color.substring(4, 6)), 16);
            // 自定义cell颜色
            HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short) index, (byte) r, (byte) g, (byte) b);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setFillForegroundColor(index);
        }
        return style;
    }

    /**
     * 
     * @Title: setFontAndBorder   
     * @Description: TODO(设置表格样式)   
     * @param: @param style
     * @param: @param fontName
     * @param: @param size
     * @param: @return      
     * @return: CellStyle      
     * @throws
     */
    public CellStyle setFontAndBorder(CellStyle style, String fontName, short size) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(fontName);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
        style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
        style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
        style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
        return style;
    }

    /**
     * 
     * @Title: deleteExcel   
     * @Description: TODO(删除文件)   
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean deleteExcel() {
        boolean flag = false;
        File file = new File(this.fileDir);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 
     * @Title: deleteExcel   
     * @Description: TODO(删除excel对象根据路径)   
     * @param: @param path
     * @param: @return      
     * @return: boolean      
     * @throws
     */
    public boolean deleteExcel(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
}