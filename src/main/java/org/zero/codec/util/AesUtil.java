package org.zero.codec.util;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author Zero (cnzeropro@qq.com)
 * @date 2022/11/29
 */
public class AesUtil {
    private static final String DEFAULT_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Base64.Decoder DECODER = Base64.getDecoder();

    private AesUtil() {
        throw new UnsupportedOperationException("Utility class could not be initialized");
    }

    /* ******************************************************* AES加密 ******************************************************* */

    /**
     * AES加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encrypt(String data, String key) {
        return encrypt(data, key, StandardCharsets.UTF_8);
    }

    /**
     * AES加密
     *
     * @param data
     * @param key
     * @return
     */
    @SneakyThrows
    public static String encrypt(String data, String key, Charset charset) {
        Cipher cipher = getCipher(key.getBytes(charset), Cipher.ENCRYPT_MODE);
        return new String(ENCODER.encode(cipher.doFinal(data.getBytes(charset))), charset);
    }

    /* ******************************************************* AES解密 ******************************************************* */

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decrypt(String data, String key) {
        return decrypt(data, key, StandardCharsets.UTF_8);
    }

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, String key) {
        return decrypt(data, key, StandardCharsets.UTF_8);
    }

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @return
     */
    @SneakyThrows
    public static byte[] decrypt(byte[] data, byte[] key) {
        Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
        return cipher.doFinal(DECODER.decode(data));
    }

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @param charset
     * @return
     */
    @SneakyThrows
    public static byte[] decrypt(byte[] data, String key, Charset charset) {
        Cipher cipher = getCipher(key.getBytes(charset), Cipher.DECRYPT_MODE);
        return cipher.doFinal(DECODER.decode(data));
    }

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @param charset
     * @return
     */
    @SneakyThrows
    public static String decrypt(String data, String key, Charset charset) {
        Cipher cipher = getCipher(key.getBytes(charset), Cipher.DECRYPT_MODE);
        return new String(cipher.doFinal(DECODER.decode(data.getBytes(charset))), charset);
    }

    /* ******************************************************* 其他 ******************************************************* */

    /**
     * 获取主体算法名
     * 例如RSA/ECB/PKCS1Padding的主体算法是RSA
     *
     * @param algorithm /算法/分块模式/填充模式
     * @return
     */
    public static String getMainAlgorithm(String algorithm) {
        final int slashIndex = algorithm.indexOf('/');
        if (slashIndex > 0) {
            return algorithm.substring(0, slashIndex);
        }
        return algorithm;
    }

    @SneakyThrows
    private static Cipher getCipher(byte[] key, int model) {
        Cipher cipher = Cipher.getInstance(DEFAULT_ALGORITHM);
        cipher.init(model, new SecretKeySpec(key, getMainAlgorithm(DEFAULT_ALGORITHM)));
        return cipher;
    }
}
