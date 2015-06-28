package com.aoxiu.utils;

import java.util.UUID;

/**
 * Created by panchao on 15/5/9.
 */
public class GeneratorCode {
    public static String generatorCode(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
