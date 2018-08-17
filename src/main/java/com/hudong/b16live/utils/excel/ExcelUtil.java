package com.hudong.b16live.utils.excel;

import com.hudong.b16live.bean.TLiveInfo;
import com.hudong.b16live.utils.DateUtil;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;


import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * ExcelUtil
 *
 * @Title: ExcelUtil.java
 * @Copyright: Copyright (c) 2005
 * @Description:
 * @Company: 百科
 * @Created on 2018/8/16 14:40
 * @Author 90
 */
public class ExcelUtil {





    /*public static  Workbook exportLive(String templatePath, String title, int startRow,
                                    List<TLiveInfo> outDataList) throws  IOException{
        ByteArrayOutputStream targetFile = new ByteArrayOutputStream();
        Workbook wb =null;
        Sheet sheet = null;
        Row row = null;
        //设置sheet名称和单元格内容
        //wb.setSheetName(0,"第一张工作表");
        wb = readExcel(templatePath);
        CellStyle cellStyle =createStyle(wb);
        if(wb != null){
            int i=0;//行
            sheet = wb.getSheetAt(0);
            if(outDataList!=null&&outDataList.size()>0){
                int max=outDataList.size();
                for (int j=0;j<max;j++){
                    TLiveInfo tLiveInfo=outDataList.get(j);
                    row=sheet.createRow(i+startRow);
                    row.createCell(0).setCellStyle(cellStyle);//样式
                    row.createCell(0).setCellValue(tLiveInfo.getId());//第一列

                    row.createCell(1).setCellStyle(cellStyle);//样式
                    row.createCell(1).setCellValue(tLiveInfo.getTitle());//第一列

                    row.createCell(2).setCellStyle(cellStyle);//样式
                    row.createCell(2).setCellValue(DateUtil.parseStringFullDate(tLiveInfo.getStartTime()));//第一列

                    row.createCell(3).setCellStyle(cellStyle);//样式
                    String status="";
                    if(3==tLiveInfo.getStatus()){
                        status="已结束";
                    }else if(2==tLiveInfo.getStatus()){
                        status="未开始";
                    } else if(3==tLiveInfo.getStatus()){
                        status="正直播";
                    }
                    row.createCell(3).setCellValue(status);//第一列

                    row.createCell(4).setCellStyle(cellStyle);//样式
                    String ishf="";
                    if(1==tLiveInfo.getIsHf()){
                        ishf="可以回放";
                    }else if(2==tLiveInfo.getIsHf()){
                        ishf="不可回放";
                    }
                    row.createCell(4).setCellValue(ishf);//第一列

                    row.createCell(5).setCellStyle(cellStyle);//样式
                    row.createCell(5).setCellValue("管理员");//第一列

                    row.createCell(6).setCellStyle(cellStyle);//样式
                    row.createCell(6).setCellValue(DateUtil.parseStringFullDate(tLiveInfo.getModTime()));//第一列

                }
            }


        }
        return wb;
    }*/

    public static void expoortExcelx(String title, String[] headers, String[] columns,
                              List<TLiveInfo> outDataList, OutputStream out) throws NoSuchMethodException, Exception{
        //创建工作薄
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建表格
        Sheet sheet=workbook.createSheet(title);
        //设置默认宽度
        sheet.setDefaultColumnWidth(25);
        //创建样式
        XSSFCellStyle style=workbook.createCellStyle();
        //设置样式
        /*style.setFillForegroundColor(IndexedColors.GOLD.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);*/
        //生成字体
        XSSFFont font=workbook.createFont();
        //font.setColor(IndexedColors.VIOLET.index);
        //font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        //应用字体
        style.setFont(font);

        //自动换行
        style.setWrapText(true);
        //声明一个画图的顶级管理器
        Drawing drawing=(XSSFDrawing) sheet.createDrawingPatriarch();
        //表头的样式
        XSSFCellStyle titleStyle=workbook.createCellStyle();//样式对象
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        //titleStyle.setVerticalAlignment(VerticalAlignment.MIDDLE);
        //titleStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);//水平居中
        //titleStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置字体
        XSSFFont titleFont=workbook.createFont();
        titleFont.setFontHeightInPoints((short)15);
        //titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体
        titleStyle.setFont(titleFont);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length-1));
        //指定合并区域
        Row rowHeader = sheet.createRow(0);
        //XSSFRow rowHeader=sheet.createRow(0);
        Cell cellHeader=rowHeader.createCell(0);
        XSSFRichTextString textHeader=new XSSFRichTextString(title);
        cellHeader.setCellStyle(titleStyle);
        cellHeader.setCellValue(textHeader);

        Row row=sheet.createRow(1);
        for(int i=0;i<headers.length;i++){
            Cell cell=row.createCell(i);
            cell.setCellStyle(style);
            XSSFRichTextString text=new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //遍历集合数据，产生数据行
        if(outDataList!=null&&outDataList.size()>0){
            int max=outDataList.size();
            int index=2;
            for (int j=0;j<max;j++){
                row=sheet.createRow(index);
                index++;
                TLiveInfo tLiveInfo=outDataList.get(j);


                String textValue=tLiveInfo.getId()+"";
                Cell cell=row.createCell(0);
                if(textValue!=null){
                    XSSFRichTextString richString = new XSSFRichTextString(textValue);
                    // HSSFFont font3 = workbook.createFont();
                    // font3.setColor(HSSFColor.BLUE.index);
                    // richString.applyFont(font3);
                    cell.setCellValue(richString);
                }
                textValue=tLiveInfo.getTitle();
                cell=row.createCell(1);
                if(textValue!=null){
                    cell.setCellValue( new XSSFRichTextString(textValue));
                }
                Date date=tLiveInfo.getStartTime();
                cell=row.createCell(2);
                if(date!=null){
                    cell.setCellValue( new XSSFRichTextString(DateUtil.parseStringFullDate(tLiveInfo.getStartTime())));
                }

                String status="";
                if(3==tLiveInfo.getStatus()){
                    status="已结束";
                }else if(2==tLiveInfo.getStatus()){
                    status="未开始";
                } else if(3==tLiveInfo.getStatus()){
                    status="正直播";
                }
                cell=row.createCell(3);
                if(status!=null){
                    cell.setCellValue( new XSSFRichTextString(status));
                }

                String ishf="";
                if(1==tLiveInfo.getIsHf()){
                    ishf="可以回放";
                }else if(2==tLiveInfo.getIsHf()){
                    ishf="不可回放";
                }
                cell=row.createCell(4);
                if(status!=null){
                    cell.setCellValue( new XSSFRichTextString(ishf));
                }
                cell=row.createCell(5);
                cell.setCellValue( new XSSFRichTextString("管理员"));

                cell=row.createCell(6);
                cell.setCellValue( new XSSFRichTextString(DateUtil.parseStringFullDate(tLiveInfo.getModTime())));


            }
        }
        /*if(list!=null&&list.size()>0){
            int index=2;
            for(T t:list){
                row=sheet.createRow(index);
                index++;
                for(short i=0;i<columns.length;i++){
                    Cell cell=row.createCell(i);
                    *//*String filedName=columns[i];
                    String getMethodName="get"+filedName.substring(0,1).toUpperCase()
                            +filedName.substring(1);
                    Class tCls=t.getClass();
                    Method getMethod=tCls.getMethod(getMethodName,new Class[]{});
                    Object value=getMethod.invoke(t, new Class[]{});
                    String textValue=null;
                    if(value==null){
                        textValue="";
                    }else if(value instanceof Date){
                        Date date=(Date)value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }else if(value instanceof byte[]){
                        row.setHeightInPoints(80);
                        sheet.setColumnWidth(i, 35*100);
                        byte[] bsValue=(byte[])value;
                        XSSFClientAnchor anchor=new XSSFClientAnchor(0,0,1023,255,6,index,6,index);
                        anchor.setAnchorType(2);
                        drawing.createPicture(anchor, workbook.addPicture(bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else{
                        // 其它数据类型都当作字符串简单处理
                        textValue=value.toString();
                    }*//*

                    if(textValue!=null){
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);
                        // HSSFFont font3 = workbook.createFont();
                        // font3.setColor(HSSFColor.BLUE.index);
                        // richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }

                }
            }
        }*/
        workbook.write(out);
    }







}
