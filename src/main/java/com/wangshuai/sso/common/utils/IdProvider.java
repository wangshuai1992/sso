package com.wangshuai.sso.common.utils;

import java.util.UUID;

/**
 * id生成工具
 *
 * @author wangshuai
 * @version V1.0
 * @date 2017-11-20 18:24
 */
public class IdProvider {

    /**
     * 通过uuid生成唯一的记录id
     *
     * @return
     */
    public static String createUUIDId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
