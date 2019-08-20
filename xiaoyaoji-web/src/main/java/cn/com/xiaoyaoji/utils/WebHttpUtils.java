package cn.com.xiaoyaoji.utils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: WebHttpUtils.java
 * @Package com.lmis.oa.common.util
 * @Description: http 请求相关
 * @author jm007092
 * @date 2019年8月14日
 * @version
 */

public class WebHttpUtils {
    private static final Logger log = LoggerFactory.getLogger(WebHttpUtils.class);

    /**
     * 不可实例化
     */

    private WebHttpUtils() {
    }

    /**
     * @Method getUrl
     * @Description 获取项目路径
     * @author chen
     * @createDate 2019年8月14日 上午10:25:39
     * @param request request
     * @return String
     */

    public static String getUrl(HttpServletRequest request) {
        String url = null;
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        if (requestAttributes != null) {
//            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        url = request.getScheme() + "://" + request.getServerName();
        // 端口
        int port = request.getServerPort();
        // 项目名
        String projectName = request.getContextPath();
        url = url + ":" + port + projectName;
        log.info("项目地址为：{}" , url);
        return url;

    }

    /**
     * 全路径
     * 
     * @param request
     * @return
     */
    public static String getUrlWhole(HttpServletRequest request) {
        String url = null;

        url = request.getScheme() + "://" + request.getServerName();
        // 端口
        int port = request.getServerPort();
        // 项目名
        String projectName = request.getContextPath();
        // 项目名后面的地址
        String projectUrl = request.getServletPath();
//      //参数
        String queryString = request.getQueryString();
//      System.out.println(queryString);
        url = url + ":" + port + projectName + projectUrl + "?" + queryString;
        log.info("项目地址为：{}" , url);
        return url;

    }

    /**
     * @Method getEngineeringUrl
     * @Description 得到工程路径 例如：D:/workspaces/.metadata/.plugins
     * @author chen
     * @createDate 2019年8月14日 上午10:26:35
     * @param request request
     * @return String
     */
    public static String getEngineeringUrl(HttpServletRequest request) {
        String url = request.getSession().getServletContext().getRealPath("");
        return url.replaceAll("\\\\", "/");

    }

    /**
     * @Method getSynchroSession
     * @Description 跨域session同步
     * @author chen
     * @createDate 2019年8月14日 上午10:24:20
     * @param servletRequest  servletRequest
     * @param servletResponse servletResponse
     * @return HttpServletRequest
     */

    public static HttpServletRequest getSynchroSession(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        res.setContentType("textml;charset=UTF-8");
        res.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "0");
        res.setHeader("Access-Control-Allow-Headers",
                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("XDomainRequestAllowed", "1");
        return request;

    }

}