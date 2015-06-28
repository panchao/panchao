package com.aoxiu.aoxiuApp.uploadPictures;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by panchao on 15/4/28.
 */
public class GeneratorToken {
    private static final String ACCESS_KEY = "wYxSvi8xm9d42z7NvbUai3jzw_G94fOpt_9jrE3m";
    private static final String SECRET_KEY = "mtPIx43Hl8zhEPXo3Fl2aNPd4TUFZIknbuRPHFOJ";
    private Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private UploadManager uploadManager = new UploadManager();
    // 简单上传，使用默认策略
    public String getUpToken0(){
        return auth.uploadToken("xuanpianer");
    }

    // 覆盖上传
    public  String getUpToken1(){
        return auth.uploadToken("xuanpianer", "key");
    }

    // 设置指定上传策略
    public  String getUpToken2(){
        return auth.uploadToken("xuanpianer", null, 3600, new StringMap()
                .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
                .put("callbackBody", "key=$(key)&hash=$(etag)"));
    }

    // 设置预处理、去除非限定的策略字段
    public  String getUpToken3(){
        return auth.uploadToken("xuanpianer", null, 3600, new StringMap()
                .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
                .putNotEmpty("persistentPipeline", ""), true);
    }

    public static void main(String [] argas){
        GeneratorToken generatorToken = new GeneratorToken();
        System.out.println("1-> " + generatorToken.getUpToken0());
        System.out.println("2-> " + generatorToken.getUpToken1());
        System.out.println("3-> " + generatorToken.getUpToken2());
        System.out.println("4-> " + generatorToken.getUpToken3());
    }

}
