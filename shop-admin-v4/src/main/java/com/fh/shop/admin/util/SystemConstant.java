package com.fh.shop.admin.util;

public class SystemConstant {
    public static final String CUURREND_USER="user";
    //当前登录用户的菜单资源集合
    public static final String LOGIN_USER_RESOURCE="menuList";
    //所有菜单集合
    public static final String RESOURCE_LIST="resourceList";
    //当前用户的所有资源
    public static final String LOGIN_USER_ALL_RESOURCE="userResourceList";
    //拦截页面
    public static final String INTERCEPTOR="/interceptor.jsp";
    public static int MAXWidth = 520;// 总宽度
    public static String GREET = "/index/indexJsp.gyh";//欢迎界面路径
    public static String MANULIST = "wealthListUrl";//当前用户持有的资源的key
    public static String ERRORJSP = "/error.jsp";//错误界面
    public static String MANU_USER="wealthList";//用户对应的资源
    public static final String COMPANY_NAME = "1902";//导出pdf文件的单位
    public static final String TEMPLATE_PATH = "/template";//模板所属文件夹
    public static final String PRODUCT_PDF_TEMPLATE_FILE = "info.html";//制定pdf模板html
    public static final Integer LOG_STATTUS_SUCCESS = 1;//日志记录
    public static final Integer LOG_STATTUS_ERROR = 0;//日志记录
}
