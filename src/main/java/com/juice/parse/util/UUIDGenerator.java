package com.juice.parse.util;

import java.util.UUID;

/**
 * 全球码生成器
 *
 * @author wangq
 * @since 14-11-29 下午5:50
 */
public class UUIDGenerator {

    public static String get(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String get(int len){
        return UUID.randomUUID().toString().replace("-", "").substring(0,len);
    }
}
