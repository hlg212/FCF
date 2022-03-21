package io.hlg212.fcf.util;

import io.hlg212.fcf.model.ImpExpModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.jxls.area.XlsArea;
import org.jxls.builder.xls.AreaCommand;
import org.jxls.command.GridCommand;
import org.jxls.command.MergeCellsCommand;
import org.jxls.command.UpdateCellCommand;
import org.jxls.common.AreaListener;
import org.jxls.common.AreaRef;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.BeanUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * 导入导出xls 帮助工具
 *
 * 通过jxls模板快速的导入导出
 *
 * @author huangligui
 * @date 2020年5月30日
 */
@Slf4j
public class XlsHelper {

    private static BeanUtilsBean myBeanUtilsBean = new MyBeanUtilsBean();

    static Map funcs = new HashMap();
    static {
        funcs.put("toolHelper",new ToolHelper());
    }



    public static void readData(InputStream in, InputStream template, Map data) throws Exception {
        // ReaderConfig.getInstance().getConvertUtilsBean().register(new DateConverter(),Date.class);

        XLSReader mainReader = ReaderBuilder.buildFromXML(template);
        //ReaderConfig.getInstance().setSkipErrors( true );
        mainReader.getConvertUtilsBeanProvider().getConvertUtilsBean().register(new DateConverter(), Date.class);
        XLSReadStatus readStatus = mainReader.read(in, data);

    }

    public static void export(InputStream template, OutputStream out, Map data) throws Exception {
        Context context = new Context(data);
        JxlsHelper.getInstance().processTemplate(template, out, context);
    }

    public static void export(InputStream template, OutputStream out, ImpExpModel data) throws Exception {
        BeanUtilsBean oldBean = BeanUtilsBean.getInstance();
        BeanUtilsBean.setInstance(myBeanUtilsBean);
        exportEh(template, out, data);
        BeanUtilsBean.setInstance(oldBean);
    }

    private static Context toContext(ImpExpModel data) {
        Context context = new Context();
        context.putVar("headers", data.getHeaders());
        context.putVar("datas", data.getDatas());
        context.putVar("title", data.getTitle());
        context.putVar("env", JsonHelper.toJsonObject(data.getEnv()));
        return context;
    }


    private static class DateConverter extends org.jxls.reader.DateConverter {

        @Override
        public Object convert(Class type, Object value) {
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                try {
                    return DateHelper.parseDate(value.toString());
                } catch (Exception e) {

                }
            }
            return super.convert(type, value);
        }
    }

    public static class  ToolHelper
    {
        private void ToolHelper(){

        }
        public static byte[] base64ToByte(String str)
        {
            return org.springframework.util.Base64Utils.decodeFromString(str);
        }
    }

    private static void setJexlEnaine(JexlExpressionEvaluator evaluator)
    {
        evaluator.setJexlEngine(new JexlBuilder().namespaces(funcs).create());
    }


    private static void exportEh(InputStream template, OutputStream out, ImpExpModel data) throws Exception
    {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformerv = jxlsHelper.createTransformer(template, out);

        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformerv.getTransformationConfig().getExpressionEvaluator();
        setJexlEnaine(evaluator);

        Context context = toContext(data);
        if( StringUtils.isEmpty( data.getExportTemplate() ) )
        {
            String props = StringUtils.join(data.getProps(), ",");
            int propsLength = data.getProps().length;
            props = props + ",";
            int maxCell = (int) 'J';

            if (propsLength > 7) {
                maxCell += propsLength - 7;
            }

            XlsArea xlsArea = new XlsArea("Sheet1!A1:J4", transformerv);

            XlsArea headArea = new XlsArea("Sheet1!A3:A3", transformerv);
            headArea.addAreaListener(new RequiredAreaListener((PoiTransformer) transformerv, getRequiredIndex(data)));
            XlsArea cellArea = new XlsArea("Sheet1!A4:A4", transformerv);
            GridCommand gridCommand = new GridCommand("headers", "datas", headArea, cellArea);
            gridCommand.setProps(props);
            gridCommand.setFormatCells("Double:J1,BigDecimal:J1,Integer:K1,Long,K1,String:L1");


            XlsArea mergeCellArea = new XlsArea("Sheet1!A1:A1", transformerv);
            MergeCellsCommand mergeCellsCommand = new MergeCellsCommand();
            mergeCellsCommand.setRows("2");
            mergeCellsCommand.setCols(propsLength + "");
            mergeCellsCommand.setMinCols("7");
            mergeCellsCommand.setMinRows("2");
            mergeCellsCommand.addArea(mergeCellArea);

            xlsArea.addCommand(new AreaRef("Sheet1!A1:G2"), mergeCellsCommand);
            xlsArea.addCommand(new AreaRef("Sheet1!A3:G4"), gridCommand);

            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()), context);
            if (data.getDatas() == null || data.getDatas().isEmpty()) {
                transformerv.clearCell(new CellRef("Sheet1!A4"));
            }

            if (transformerv instanceof PoiTransformer) {
                ((PoiTransformer) transformerv).getWorkbook().setSheetName(0, data.getTitle());
            }
        }
        jxlsHelper.processTemplate(context,transformerv);
    }

    private static List getRequiredIndex(ImpExpModel data)
    {

        if( data.getRequiredProps() == null || data.getRequiredProps().length == 0 )
        {
            return null;
        }
        List res = new ArrayList();
        List reProps = Arrays.asList(data.getRequiredProps());
        String props[] = data.getProps();
        for( int i=0;i<props.length;i++)
        {
            if( reProps.contains(props[i]) )
            {
                res.add(i);
            }
        }
        return res;
    }


    public static class MyBeanUtilsBean extends BeanUtilsBean
    {
        @Override
        public PropertyUtilsBean getPropertyUtils() {
            return new NullPropertyUtilsBean();
        }

    }
    public static class NullPropertyUtilsBean extends  PropertyUtilsBean
    {
        @Override
        public Object getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
            try {
                Object o = super.getProperty(bean, name);

                return o;
            }catch(Exception e)
            {
                return null;
            }
        }
    }

    private static class RequiredAreaListener implements AreaListener
    {
        private PoiTransformer transformer;

        private List<Integer> requireds;

        public RequiredAreaListener(PoiTransformer transformer, List<Integer> requireds) {
            this.transformer = transformer;
            this.requireds = requireds;
        }

        @Override
        public void beforeApplyAtCell(CellRef cellRef, Context context) {

        }

        @Override
        public void afterApplyAtCell(CellRef cellRef, Context context) {

        }

        @Override
        public void beforeTransformCell(CellRef srcCell, CellRef targetCell, Context context) {

        }

        @Override
        public void afterTransformCell(CellRef srcCell, CellRef targetCell, Context context) {
            if( requireds == null || requireds.isEmpty() || !(targetCell.getRow() == 2) )
            {
                return;
            }

            if(  requireds.contains( targetCell.getCol() ) ) {
                Workbook workbook = transformer.getWorkbook();
                Cell cell = workbook.getSheet(targetCell.getSheetName()).getRow(targetCell.getRow()).getCell(targetCell.getCol());
               try {
                   if (cell != null) {
                       log.debug("标注必填[{}]失败",cell.getStringCellValue());
                       Font font = workbook.createFont();
                       CellStyle cellStyle = workbook.createCellStyle();
                       cellStyle.cloneStyleFrom(cell.getCellStyle());
                       cellStyle.setFont(font);
                       //cellStyle.setDataFormat();
                       font.setColor(XSSFFont.COLOR_RED);
                       //resultCell.setFont(font);
                       //cell.setCellStyle(resultCell);
                       // cell.getCellStyle().setFont(font);
                       //cell.getCellStyle().setFillBackgroundColor(XSSFFont.COLOR_RED);
                       Font font1 = ((XSSFCell) cell).getCellStyle().getFont();
                       font.setFontHeightInPoints(font1.getFontHeightInPoints());
                       font.setFontName(font1.getFontName());
                       font.setBold(font1.getBold());

                       cell.setCellStyle(cellStyle);
                   }
               }catch (Exception e){
                   log.warn("标注必填[{}]失败,请检查",cell.getStringCellValue(),e);
               }
            }
        }
    }


}
