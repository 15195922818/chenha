package com.ai.core.util.utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 验证码工具类
 *
 * @author peiyu
 */
public final class CaptchaUtils {

    /**
     * 获得数字验证码
     *
     * @param howLong 想要获取的验证码长度
     * @return 验证码内容
     */
    public static final String getCaptchaCode(int howLong) {
        String result = null;
        if (howLong > 0) {
            double minNumber = Math.pow(10, (double) (howLong - 1));
            result = String.valueOf(ThreadLocalRandom.current().nextInt((int) minNumber, (int) minNumber * 10));
        }
        return result;
    }
}
