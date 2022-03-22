package  io.github.hlg212.fcf.web.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/** 
 * 封装servelt环境
 * 业务中如果需要request对象,可以直接通过该工具获取
 * 业务控制器的方法声明可以不再需要接受request对象参数
 * 降低业务环境对servlet环境的依赖
 * request对象被存储到线程环境中,只要是同一个线程都可调用
 * 
 * ClassName: HttpServletHelper
 * Function: TODO ADD FUNCTION. 
 * Reason: servelt 工具类
 * date: 2017年8月22日 下午7:12:16
 * 
 * @author huangligui 
 */
public class HttpServletHelper {

    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        return request;
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes) ra).getResponse();
        return response;
    }
    
    public static HttpSession getSession() {
    	return getRequest().getSession();
    }

    
    public static String getBasePath()
    {
    	HttpServletRequest request = getRequest();
    	String path = request.getContextPath();
	    String basePath = null;
	    int port = request.getServerPort();
	    if( port != 80 )
	    {
	    	basePath = request.getScheme() + "://" + request.getServerName() + ":" + port;
	    }
	    else
	    {
	    	basePath = request.getScheme() + "://" + request.getServerName();
	    }
	    return basePath + path;
    }
    
    public static String getClientIp(){
    	return getClientIp(getRequest());
    }
    
    public static String getClientIp(HttpServletRequest request){
		String ip = request.getHeader("X-forwarded-for") ;
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	      ip  = request.getHeader("Proxy-Client-IP");    
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	    	ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	    	ip = request.getHeader("HTTP-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	    	ip = request.getHeader("HTTP_X_FORWARDED-FOR");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	    	ip = request.getRemoteAddr();
	    }
	    if( ip != null )
	    {
	    	String ips[] = ip.split(",");
	    	ip = ips[0];
	    }	    
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
		 
	}
    
}
