package com.blogadmin.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * excel导出工具类
 * @ClassName: ExcelUtil
 * @author liqifan
 * @date 2016年7月1日 上午11:05:01 
 *
 */
public class ExcelUtil {
    private static XSSFWorkbook wb;

    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体

    /**
     * 导出excel
     * 
     * @param setInfo
     * @return
     * @throws Exception
     */
    public static XSSFWorkbook exportExcelFile(ExcelExportData setInfo)
            throws Exception {
        
    	init();
        Set<Entry<String, List<?>>> set = setInfo.getDataMap().entrySet();
        String[] sheetNames = new String[setInfo.getDataMap().size()];
        int sheetNameNum = 0;
        for (Entry<String, List<?>> entry : set) {
            sheetNames[sheetNameNum] = entry.getKey();
            sheetNameNum++;
        }
        XSSFSheet[] sheets = getSheets(setInfo.getDataMap().size(), sheetNames);
        int sheetNum = 0;
        for (Entry<String, List<?>> entry : set) {
            // Sheet
            List<?> objs = entry.getValue();

            // 标题行
            createTableTitleRow(setInfo, sheets, sheetNum);

            // 日期行
            createTableDateRow(setInfo, sheets, sheetNum);

            // 表头
            creatTableHeadRow(setInfo, sheets, sheetNum);

            // 列code
            String[] fieldNames = setInfo.getFieldNames().get(sheetNum);
            
            // 列name
            String[] columNames = setInfo.getColumnNames().get(sheetNum);
            
            // 列宽
            Map<Integer,Integer> cellWidth = new HashMap<Integer,Integer>();
            
            int rowNum = 3;
            for (Object obj : objs) {
                XSSFRow contentRow = sheets[sheetNum].createRow(rowNum);
                contentRow.setHeight((short) 300);
                XSSFCell[] cells = getCells(contentRow, setInfo.getFieldNames()
                        .get(sheetNum).length);
                int cellNum = 1; // 去掉一列序号，因此从1开始
                int fieldIndex = 0;
                int valueWidth = 0;
                int titleWidth = 0;
                int maxWidth = 0;
                @SuppressWarnings("unchecked")
				Map<String,Object> map = (Map<String, Object>) obj;
                for (String key : map.keySet()) {
                	valueWidth = map.get(fieldNames[fieldIndex]).toString().length();
                	titleWidth = columNames[fieldIndex].length()*2;
                	if(valueWidth >= titleWidth){
                		maxWidth = valueWidth;
                	}else{
                		maxWidth = titleWidth;
                	}
                	if(cellWidth.get(fieldIndex) == null || cellWidth.get(fieldIndex) <= maxWidth){
                		cellWidth.put(fieldIndex, maxWidth);
                	}
                    cells[cellNum].setCellValue(map.get(fieldNames[fieldIndex]) == null ? "" : map.get(fieldNames[fieldIndex]).toString());
                    cellNum++;
                    if(fieldIndex+1 < fieldNames.length){
                    	fieldIndex++;
                    }else{
                    	break;
                    }
                }
                rowNum++;
            }
            adjustColumnSize(sheets, sheetNum, fieldNames,cellWidth); // 自动调整列宽
            sheetNum++;
        }
        
        return wb;
    }

    /**
     * @Description: 初始化
     */
    private static void init() {
        wb = new XSSFWorkbook();

        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();

        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
    }

    /**
     * @Description: 自动调整列宽
     */
    private static void adjustColumnSize(XSSFSheet[] sheets, int sheetNum,
            String[] fieldNames, Map<Integer,Integer> cellWidth) {
        for (int i = 0; i < fieldNames.length; i++) {
        	if(cellWidth.get(i) != null){
        		sheets[sheetNum].setColumnWidth(i+1, cellWidth.get(i)*256);
        	}
        }
    }

    /**
     * @Description: 创建标题行(需合并单元格)
     */
    private static void createTableTitleRow(ExcelExportData setInfo,
            XSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, setInfo
                .getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(titleRange);
        XSSFRow titleRow = sheets[sheetNum].createRow(0);
        titleRow.setHeight((short) 800);
        XSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(setInfo.getTitles()[0]);
    }

    /**
     * @Description: 创建日期行(需合并单元格)
     */
    private static void createTableDateRow(ExcelExportData setInfo,
            XSSFSheet[] sheets, int sheetNum) {
        CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, setInfo
                .getFieldNames().get(sheetNum).length);
        sheets[sheetNum].addMergedRegion(dateRange);
        XSSFRow dateRow = sheets[sheetNum].createRow(1);
        dateRow.setHeight((short) 350);
        XSSFCell dateCell = dateRow.createCell(0);
        dateCell.setCellStyle(dateStyle);
        // dateCell.setCellValue("导出时间：" + new
        // SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        // .format(new Date()));
        dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date()));
    }

    /**
     * @Description: 创建表头行(需合并单元格)
     */
    private static void creatTableHeadRow(ExcelExportData setInfo,
            XSSFSheet[] sheets, int sheetNum) {
        // 表头
        XSSFRow headRow = sheets[sheetNum].createRow(2);
        headRow.setHeight((short) 350);
        // 序号列
        XSSFCell snCell = headRow.createCell(0);
        snCell.setCellStyle(headStyle);
        snCell.setCellValue("序号");
        // 列头名称
        for (int num = 1, len = setInfo.getColumnNames().get(sheetNum).length; num <= len; num++) {
            XSSFCell headCell = headRow.createCell(num);
            headCell.setCellStyle(headStyle);
            headCell.setCellValue(setInfo.getColumnNames().get(sheetNum)[num - 1]);
        }
    }

    /**
     * @Description: 创建所有的Sheet
     */
    private static XSSFSheet[] getSheets(int num, String[] names) {
        XSSFSheet[] sheets = new XSSFSheet[num];
        for (int i = 0; i < num; i++) {
            sheets[i] = wb.createSheet(names[i]);
        }
        return sheets;
    }

    /**
     * @Description: 创建内容行的每一列(附加一列序号)
     */
    private static XSSFCell[] getCells(XSSFRow contentRow, int num) {
        XSSFCell[] cells = new XSSFCell[num + 1];

        for (int i = 0, len = cells.length; i < len; i++) {
            cells[i] = contentRow.createCell(i);
            cells[i].setCellStyle(contentStyle);
        }

        // 设置序号列值，因为出去标题行和日期行，所有-2
        cells[0].setCellValue(contentRow.getRowNum() - 2);

        return cells;
    }

    /**
     * @Description: 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * @Description: 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * @Description: 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        headStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
    }

    /**
     * @Description: 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setLeftBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());
        contentStyle.setWrapText(true); // 字段换行
    }

    /**
     * @Description: 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * @Description: 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * @Description: 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setCharSet(Font.DEFAULT_CHARSET);
        headFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }

    /**
     * @Description: 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.BLUE_GREY.getIndex());
    }
    
    /**
     * Excel导出数据类
     * @ClassName: ExcelExportData
     * @author liqifan
     * @date 2016年7月1日 上午11:04:38 
     *
     */
    public static class ExcelExportData {

        /**
         * 导出数据 key:String 表示每个Sheet的名称 value:List<?> 表示每个Sheet里的所有数据行
         */
        private LinkedHashMap<String, List<?>> dataMap;

        /**
         * 每个Sheet里的顶部大标题
         */
        private String[] titles;

        /**
         * 单个sheet里的数据列标题
         */
        private List<String[]> columnNames;

        /**
         * 单个sheet里每行数据的列对应的对象属性名称
         */
        private List<String[]> fieldNames;

        public List<String[]> getFieldNames() {
            return fieldNames;
        }

        public void setFieldNames(List<String[]> fieldNames) {
            this.fieldNames = fieldNames;
        }

        public String[] getTitles() {
            return titles;
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }

        public List<String[]> getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(List<String[]> columnNames) {
            this.columnNames = columnNames;
        }

        public LinkedHashMap<String, List<?>> getDataMap() {
            return dataMap;
        }

        public void setDataMap(LinkedHashMap<String, List<?>> dataMap) {
            this.dataMap = dataMap;
        }

    }
}