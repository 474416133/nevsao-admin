package cn.nevsao.common.util;

import cn.nevsao.common.annotation.Dict;
import cn.nevsao.common.annotation.ExportConfig;
import cn.nevsao.common.handler.ExportHandler;
import cn.nevsao.common.util.poi.Config;
import cn.nevsao.common.util.poi.convert.ExportConvert;
import cn.nevsao.common.util.poi.pojo.ExportItem;
import cn.nevsao.system.dept.entity.Dept;
import cn.nevsao.system.user.entity.User;
import com.csvreader.CsvWriter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

public class ExcelUtils {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private Class<?> mClass = null;
    private HttpServletResponse mResponse = null;
    // 分Sheet机制：每个Sheet最多多少条数据
    private Integer mMaxSheetRecords = 10000;
    // 缓存数据格式器实例,避免多次使用反射进行实例化
    private Map<String, ExportConvert> mConvertInstanceCache = new HashMap<>();

    protected ExcelUtils() {
    }

    private ExcelUtils(Class<?> clazz) {
        this(clazz, null);
    }

    private ExcelUtils(Class<?> clazz, HttpServletResponse response) {
        this.mResponse = response;
        this.mClass = clazz;
    }

    /**
     * 用于生成本地文件
     *
     * @param clazz 实体Class对象
     * @return ExcelUtils
     */
    static ExcelUtils builder(Class<?> clazz) {
        return new ExcelUtils(clazz);
    }

    /**
     * 用于浏览器导出
     *
     * @param clazz    实体Class对象
     * @param response 原生HttpServletResponse对象
     * @return ExcelUtils
     */
    public static ExcelUtils export(Class<?> clazz, HttpServletResponse response) {
        return new ExcelUtils(clazz, response);
    }

    /**
     * 分Sheet机制：每个Sheet最多多少条数据(默认10000)
     *
     * @param size 数据条数
     * @return this
     */
    public ExcelUtils setMaxSheetRecords(Integer size) {
        this.mMaxSheetRecords = size;
        return this;
    }

    /**
     * 导出Excel(此方式需依赖浏览器实现文件下载,故应先使用export()构造器)
     *
     * @param data      数据集合
     * @param sheetName 工作表名字
     * @return true-操作成功,false-操作失败
     */
    public boolean toExcel(List<?> data, String sheetName) {
        requiredexportParams();

        try {
            return toExcel(data, sheetName, mResponse.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 针对转换方法的默认实现(提供默认样式和文件命名规则)
     *
     * @param data      数据集合
     * @param sheetName 工作表名字
     * @param out       输出流
     * @return true-操作成功,false-操作失败
     */
    boolean toExcel(List<?> data, String sheetName, OutputStream out) {

        return toExcel(data, sheetName, new ExportHandler() {

            @Override
            public CellStyle headCellStyle(SXSSFWorkbook wb) {
                CellStyle cellStyle = wb.createCellStyle();
                Font font = wb.createFont();
                cellStyle.setFillForegroundColor((short) 12);
                cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
                cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框为细边框
                cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框为细边框
                cellStyle.setBorderBottom(CellStyle.BORDER_THIN);// 下边框为细边框
                cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框为细边框
                cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 对齐
                cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
                cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
                font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
                // font.setFontHeightInPoints((short) 12);// 字体大小
                font.setColor(HSSFColor.WHITE.index);
                // 应用标题字体到标题样式
                cellStyle.setFont(font);
                return cellStyle;
            }

            @Override
            public String exportFileName(String sheetName) {
                return String.format("导出-%s-%s", sheetName, System.currentTimeMillis());
            }
        }, out);
    }

    private boolean toExcel(List<?> data, String sheetName, ExportHandler handler, OutputStream out) {
        requiredbuilderParams();
        if (data == null || data.isEmpty()) {
            return false;
        }
        // 导出列查询。
        ExportConfig currentExportConfig;
        ExportItem currentExportItem;
        List<ExportItem> exportItems = new ArrayList<>();
        for (Field field : mClass.getDeclaredFields()) {

            currentExportConfig = field.getAnnotation(ExportConfig.class);
            if (currentExportConfig != null) {
                currentExportItem = new ExportItem().setField(field.getName())
                        .setDisplay("field".equals(currentExportConfig.value()) ? field.getName()
                                : currentExportConfig.value())
                        .setWidth(currentExportConfig.width()).setConvert(currentExportConfig.convert())
                        .setColor(currentExportConfig.color()).setReplace(currentExportConfig.replace());
                exportItems.add(currentExportItem);
            }

        }

        // 创建新的工作薄。
        SXSSFWorkbook wb = POIUtils.newSXSSFWorkbook();

        double sheetNo = Math.ceil((double) data.size() / mMaxSheetRecords);// 取出一共有多少个sheet.

        // =====多sheet生成填充数据=====
        int index = 0;
        while (index <= (sheetNo == 0.0 ? sheetNo : sheetNo - 1)) {
            SXSSFSheet sheet = POIUtils.newSXSSFSheet(wb, sheetName + (index == 0 ? "" : "_" + index));

            // 创建表头
            SXSSFRow headerRow = POIUtils.newSXSSFRow(sheet, 0);
            for (int i = 0; i < exportItems.size(); i++) {
                SXSSFCell cell = POIUtils.newSXSSFCell(headerRow, i);
                POIUtils.setColumnWidth(sheet, i, exportItems.get(i).getWidth(), exportItems.get(i).getDisplay());
                cell.setCellValue(exportItems.get(i).getDisplay());

                CellStyle style = handler.headCellStyle(wb);
                if (style != null) {
                    cell.setCellStyle(style);
                }
            }

            SXSSFRow bodyRow;
            String cellValue;
            SXSSFCell cell;
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            style.setFont(font);

            // 产生数据行
            if (!data.isEmpty()) {
                int startNo = index * mMaxSheetRecords;
                int endNo = Math.min(startNo + mMaxSheetRecords, data.size());

                int i = startNo;
                while (i < endNo) {
                    bodyRow = POIUtils.newSXSSFRow(sheet, i + 1 - startNo);
                    for (int j = 0; j < exportItems.size(); j++) {
                        // 处理单元格值
                        cellValue = exportItems.get(j).getReplace();
                        if ("".equals(cellValue)) {
                            try {
                                cellValue = BeanUtils.getProperty(data.get(i), exportItems.get(j).getField());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                        }

                        // 格式化单元格值
                        if (!"".equals(exportItems.get(j).getConvert())) {
                            cellValue = convertCellValue(cellValue, exportItems.get(j).getConvert());
                        }

                        // 单元格宽度
                        POIUtils.setColumnWidth(sheet, j, exportItems.get(j).getWidth(), cellValue);

                        cell = POIUtils.newSXSSFCell(bodyRow, j);
                        // fix: 当值为“”时,当前index的cell会失效
                        cell.setCellValue("".equals(cellValue) ? null : cellValue);
                        cell.setCellStyle(style);
                    }
                    i++;
                }
            }
            index++;
        }

        try {
            // 生成Excel文件并下载.(通过response对象是否为空来判定是使用浏览器下载还是直接写入到output中)
            POIUtils.writeByLocalOrBrowser(mResponse, handler.exportFileName(sheetName), wb, out);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }

    boolean toCsv(List<?> data, String path) {
        try {
            requiredbuilderParams();
            if (data == null || data.isEmpty()) {
                return false;
            }

            // 导出列查询。
            ExportConfig currentExportConfig;
            ExportItem currentExportItem;
            List<ExportItem> exportItems = new ArrayList<>();
            for (Field field : mClass.getDeclaredFields()) {

                currentExportConfig = field.getAnnotation(ExportConfig.class);
                if (currentExportConfig != null) {
                    currentExportItem = new ExportItem().setField(field.getName())
                            .setDisplay("field".equals(currentExportConfig.value()) ? field.getName()
                                    : currentExportConfig.value())
                            .setConvert(currentExportConfig.convert()).setReplace(currentExportConfig.replace());
                    exportItems.add(currentExportItem);
                }

            }

            String cellValue;
            FileOutputStream out = new FileOutputStream(path);
            // 解决乱码
            out.write(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            CsvWriter csvWriter = new CsvWriter(out, ',', Charset.forName("UTF-8"));
            String[] csvHeadersArr = exportItems.stream().map(ExportItem::getDisplay).toArray(String[]::new);
            csvWriter.writeRecord(csvHeadersArr);
            for (Object aData : data) {
                List<Object> csvContent = new ArrayList<>();
                for (ExportItem exportItem : exportItems) {
                    // 处理单元格值
                    cellValue = exportItem.getReplace();
                    if (!StringUtils.isNotBlank(cellValue)) {
                        cellValue = BeanUtils.getProperty(aData, exportItem.getField());
                    }

                    // 格式化单元格值
                    if (StringUtils.isNotBlank(exportItem.getConvert())) {
                        cellValue = convertCellValue(cellValue, exportItem.getConvert());
                    }
                    csvContent.add(cellValue);
                }
                String[] csvContentArr = csvContent.toArray(new String[0]);
                csvWriter.writeRecord(csvContentArr);
            }
            csvWriter.close();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    // convertCellValue: number to String
    private String convertCellValue(Object oldValue, String format) {
        try {
            String protocol = format.split(":")[0];

            // 键值对字符串解析：s:1=男,2=女
            if ("s".equalsIgnoreCase(protocol)) {

                String[] pattern = format.split(":")[1].split(",");
                for (String p : pattern) {
                    String[] cp = p.split("=");
                    if (cp[0].equals(oldValue)) {
                        return cp[1];
                    }
                }

            }
            if ("c".equalsIgnoreCase(protocol)) {

                String clazz = format.split(":")[1];
                ExportConvert export = mConvertInstanceCache.get(clazz);
                if (export == null) {
                    export = (ExportConvert) Class.forName(clazz).newInstance();
                    mConvertInstanceCache.put(clazz, export);
                }

                if (mConvertInstanceCache.size() > 10)
                    mConvertInstanceCache.clear();

                return export.handler(oldValue);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return String.valueOf(oldValue);
    }

    private void requiredbuilderParams() {
        if (mClass == null) {
            throw new IllegalArgumentException("请先使用cc.nevsao.util.ExcelUtils.builder(Class<?>)构造器初始化参数。");
        }
    }

    private void requiredexportParams() {
        if (mClass == null || mResponse == null) {
            throw new IllegalArgumentException(
                    "请先使用cc.nevsao.util.ExcelUtils.export(Class<?>, HttpServletResponse)构造器初始化参数。");
        }

    }


    public static  List parseXls(Workbook wb, Class clz){
        //Workbook wb = POIUtils.readFile(file);
        Map<String, Field> fieldMap = getClassExportMeta(clz);
        List list = new ArrayList();
        if (wb == null){
            return null;
        }
        //获取第一个sheet
        Sheet sheet = wb.getSheetAt(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        Row row = sheet.getRow(0);
        for (int i = 1; i < rowNum; i++){
            Map<String, String> data = new HashMap<>();
            Row tempRow = sheet.getRow(i);
            Object entity = buildObject(row, tempRow, fieldMap);
            if (entity != null){
                list.add(entity);
            }

        }
        return list;
    }

    private static Object buildObject (Row title, Row data, Map<String, Field> fieldMap)  {
        Object object = null;
        for (int i = 0; i < title.getPhysicalNumberOfCells(); i++){
            String titleName = title.getCell(i).getStringCellValue();
            data.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            String value = data.getCell(i).getStringCellValue();
            Field field = fieldMap.get(titleName);
            if (field == null){
                continue;
            }
            if (object == null){
                Class clz = field.getDeclaringClass();
                try {
                    object = clz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (object == null){
                    break;
                }
            }
            field.setAccessible(true);
            setFieldValue(field, value, object);
        }
        return object;
    };

    private static Map<String, Field> getClassExportMeta(Class clz){
        Map<String, Field> map = new HashMap<>();
        for (Field field: clz.getDeclaredFields()){
            ExportConfig exportConfig = field.getAnnotation(ExportConfig.class);
            if (exportConfig != null){
                map.put(exportConfig.value(), field);
            }
        }
        return map;
    }

    private static void setFieldValue(Field field, String value, Object object) {
        Method method = null;
        try {
            method = field.getType().getMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try{
            if (field.getType() == Date.class){
                field.set(object, DateUtil.parseDateFormat(value));
            } else if (field.getType() == Boolean.class){
                field.set(object, Config.bitSet.contains(value));
            } else if (field.getType() == Character.class){
                field.set(object, value.charAt(0));
            }else if (method != null){
                    method.invoke(object, value);
            }else{
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Workbook createByInputStream(String suffix, InputStream stream) throws IOException {
        Workbook wb = null;
        if ("xls".equals(suffix)) {
            wb = new HSSFWorkbook(stream);
        } else if ("xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(stream);
        }

        return wb;
    }

    public static List parseFile(MultipartFile file, Class clz){
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.')+1);
        try {
            Workbook wb = createByInputStream(suffix, file.getInputStream());
            return parseXls(wb, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static  void main(String[] args) {
        Workbook wb = readExcel("/home/sven/user.xlsx");
        List list = parseXls(wb, User.class);
        System.out.println("ok");
    }

}
