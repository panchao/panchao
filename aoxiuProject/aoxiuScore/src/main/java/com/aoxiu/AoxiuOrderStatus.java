package com.aoxiu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by panchao on 15/6/14.
 */
public class AoxiuOrderStatus {
    public static Map<String,String> orderStatus = new HashMap<>();
    public static String ORIGIN_PAGE_NOT_GENERATED = "originPageNotGenerated";    //未生成原片页面
    public static String ORIGIN_NOT_UPLOADED = "originNotUploaded";               //未上传原片
    public static String SELECT_TRIM_INFO_NOT_SUBMITTED = "selectTrimInfoNotSubmitted"; //用户没有提交精修片
    public static String SELECTED_NOT_UPLOADED = "selectedNotUploaded";     //未上传精修片
    public static String UNCOMMENTED = "uncommented";   //没有被评价的
    public static String FINISH = "finish";   //完成

    static {
        orderStatus.put(ORIGIN_PAGE_NOT_GENERATED,"上传原片");
        orderStatus.put(SELECT_TRIM_INFO_NOT_SUBMITTED,"用户选定修片");
        orderStatus.put(SELECTED_NOT_UPLOADED,"上传精修片");
        orderStatus.put(UNCOMMENTED,"完成评价");
        orderStatus.put(FINISH,"完成");
        orderStatus.put(ORIGIN_NOT_UPLOADED,"未上传原片");
    }
}
