package com.fly.caipiao.analysis.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author baidu
 * @date 2018/7/3 下午2:35
 * @description 加密
 **/
public final class MD5Encrypt {

    private static final ThreadLocal<MD5Encrypt> local = new ThreadLocal<>();

    private MD5Encrypt() {
        super();
    }

    public static MD5Encrypt getEncrypt() {
        MD5Encrypt encrypt = local.get();
        if (encrypt == null) {
            encrypt = new MD5Encrypt();
            local.set(encrypt);
        }
        return encrypt;
    }

    public String encode(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(s);
    }

}
