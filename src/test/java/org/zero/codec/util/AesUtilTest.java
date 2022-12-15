package org.zero.codec.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
class AesUtilTest {
    private static final String KEY = "0123456789abcdef";

    @Test
    void encrypt() {
        System.out.println(AesUtil.encrypt("你好@123abc！", KEY));
    }

    @Test
    void decrypt() {
        System.out.println(AesUtil.decrypt("klnFMOy5QnUdRs2roLP2RzdyIuBhqSTFkc2cJ+oWPtQ=", KEY));
    }
}