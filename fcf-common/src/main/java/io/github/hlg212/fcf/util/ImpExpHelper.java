package  io.github.hlg212.fcf.util;

import  io.github.hlg212.fcf.model.ImpExpModel;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;


/**
 * 导入帮助工具
 *
 * @author huangligui
 * @date 2020年5月30日
 */
public class ImpExpHelper {

    public static final String  IMPORT_TEMPLATE_PATH = "imexTemplate/import_template.xml";
    public static final String  EXPORT_TEMPLATE_PATH = "imexTemplate/export_template.xlsx";

    private static OutputStream createXml(ImpExpModel ieo) throws Exception
    {
        InputStream in = null;
        if( ieo != null )
        {
            if( StringUtils.isNotEmpty( ieo.getImportTemplate() ) )
            {
                try {
                    in = ResourceHelper.getInputStream(ieo.getImportTemplate());
                } catch (Exception e) {
                    ExceptionHelper.throwBusinessException("模板文件不存在!");
                }
            }
        }
        if( in == null )
            in = ResourceHelper.getInputStream(IMPORT_TEMPLATE_PATH);

        Document document =  DocumentHelper.readDocument(in);
        appendDocument(ieo,document);

        TransformerFactory formerFactory=TransformerFactory.newInstance();
        Transformer transformer=formerFactory.newTransformer();
        // 换行
        transformer.setOutputProperty(OutputKeys.INDENT, "YES");
        // 文档字符编码
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //transformer.transform(new DOMSource(document),new StreamResult(new File("D://YyImportXmlGenTest.xml")));
        transformer.transform(new DOMSource(document),new StreamResult(outputStream));
        return outputStream;
    }
    public static  InputStream getExportTemplate(ImpExpModel ieo)
    {
        if( ieo != null )
        {
            if( StringUtils.isNotEmpty( ieo.getExportTemplate() ) )
            {
                try {
                   return ResourceHelper.getInputStream(ieo.getExportTemplate());
                } catch (Exception e) {
                    ExceptionHelper.throwBusinessException("模板文件不存在!");
                }
            }
        }
        try {
            return ResourceHelper.getInputStream(ImpExpHelper.EXPORT_TEMPLATE_PATH);
        } catch (Exception e) {
            ExceptionHelper.throwBusinessException("模板文件不存在!");
        }
        return null;
    }


    private static void appendDocument(ImpExpModel ieo ,Document document)
    {
        Class c = getWapClass(ieo);
        document.getElementsByTagName("loop").item(0).getAttributes().getNamedItem("varType").setNodeValue(c.getName());
        appendEnv(ieo,document);
        appendChild("3",ieo.getProps(),document);
    }

    private static void appendEnv(ImpExpModel ieo ,Document document)
    {
        int length = ieo.getProps().length;
        int col =  length > 7 ? length + 1 : 8;
        NodeList list = document.getElementsByTagName("mapping");
        list.item(0).getAttributes().getNamedItem("col").setNodeValue(col + "");
        list.item(1).getAttributes().getNamedItem("col").setNodeValue(col + "");
    }

    private static void  appendChild(String row,String props[],Document document )
    {
        Node section =  document.getElementsByTagName("section").item(1);
        int i=0;
        for( String p : props )
        {
            Element element = document.createElement("mapping");
            element.setAttribute("row",row);
            element.setAttribute("col",i+"");
            element.setAttribute("nullAllowed","true");
            element.setTextContent("data."+p);
            section.appendChild(element);
            i++;
        }
    }


    public static InputStream createXmlInputStream(ImpExpModel ieo) throws Exception
    {
        try(
                ByteArrayOutputStream out = (ByteArrayOutputStream)createXml(ieo);
                 ByteArrayInputStream byteInputStream = new ByteArrayInputStream(out.toByteArray());
        ) {
            return byteInputStream;
        }
    }

    private static Class getWapClass(ImpExpModel ieo)
    {
        Enhancer enhancer = new Enhancer();
        //设置要创建动态代理的类
        enhancer.setSuperclass(ieo.getEntityClass());
        //enhancer.setCallbackType(NoOp.class);
        // enhancer.setCallback(new ImportWarpEnhancer());
        //enhancer.setCallbackFilter(new MCallbackFilter());
        //enhancer.setCallbackTypes(new Class[]{ImportWarpEnhancer.class});
        enhancer.setCallbackTypes(new Class[]{ImportWarpEnhancer.class});
        Class subClass = enhancer.createClass();

        Map map  = getNestProps(ieo.getProps());

        Enhancer.registerCallbacks(subClass, new Callback[]{new ImportWarpEnhancer(map)});
        return subClass;
    }

    private static Map getNestProps(String props[])
    {
        Map<String,Collection> map  = new HashMap();

        for( String p : props )
        {
            if( p.indexOf(".") != -1)
            {
                String pn = p.substring(0,p.lastIndexOf("."));
                String pns[] = pn.split("\\.");
                Collection collection = map.get(pns[0]);
                if( collection == null )
                {
                    collection = new ArrayList();
                    collection.add(pns[0]);
                    map.put(pns[0],collection);
                }
                if( pns.length > 1) {
                    collection.add(pn);
                }
            }
        }

        return map;
    }


    private static class ImportWarpEnhancer implements MethodInterceptor
    {
        private Map<String,Collection> nestProperty;

        public ImportWarpEnhancer(Map nestProperty)
        {
            Set<Map.Entry> set = nestProperty.entrySet();
            nestProperty = new HashMap();
            for(Map.Entry entry :  set )
            {
                nestProperty.put( ("get"+entry.getKey().toString()).toUpperCase() ,entry.getValue());
            }
            this.nestProperty = nestProperty;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

            if( nestProperty != null && nestProperty.containsKey(method.getName().toUpperCase()) )
            {
                Object child =  methodProxy.invokeSuper(o,args);
                if( child == null ) {
                    List<String> props = new ArrayList<>();
                    props.addAll( nestProperty.get(method.getName().toUpperCase()) );
                    String childName = props.remove(0);
                    Class c = BeanUtils.findPropertyType(childName, o.getClass());
                    child = c.newInstance();
                    PropertyUtils.setProperty(o, childName, child);
                    newNestProperty(props,child);
                }
            }
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }
            return methodProxy.invokeSuper(o, args);
        }

        private  void newNestProperty(String p,Object obj) throws Exception {
            String ps[] = p.split(",");
            Object pobj = obj;
            for( String pname : ps )
            {
                Object child = PropertyUtils.getProperty(pobj,pname);
                if( child == null ) {
                    Class c = BeanUtils.findPropertyType(pname, pobj.getClass());
                    child = c.newInstance();
                    PropertyUtils.setProperty(pobj, pname, child);
                }
                pobj = child;
            }

        }


        private  void newNestProperty(Collection<String> ps,Object obj) throws Exception {
            for(String p : ps )
            {
                newNestProperty(p,obj);
            }
        }
    }




}
