package com.example.biologyModel.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.example.biologyModel.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@Component
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private final String VOLUNTEER = "volunteer";
    private final int expireDate = 3;

    private static byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    private static SymmetricCrypto aes = SecureUtil.aes(key);

    public String generateUserToken(long userId, HttpServletRequest request, HttpServletResponse response) {
        long timeStamp = System.currentTimeMillis();

        String token = encrypt(userId + "_" + timeStamp + "_" + VOLUNTEER);

        response.setHeader(CommonConstant.TOKEN, token);

        return token;
    }

    public String getToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(CommonConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }

        String decryptContent = null;
        String userId;
        try {
            decryptContent = decrypt(token);
            if (StringUtils.isBlank(decryptContent)) {
                return null;
            }
            String[] strs = decryptContent.split("_");
            userId = strs[0];
            String timestamp = strs[1];
            Date expireTime = DateUtils.addDays(new Date(Long.parseLong(timestamp)), expireDate);
            String sign = strs[2];
            if (StringUtils.isBlank(userId) || expireTime.before(new Date()) || !VOLUNTEER.equals(sign)) {
                return null;
            }
        } catch (Exception e) {
            logger.error("[getToken Exception], token: {}, decryptContent: {}", token, decryptContent, e);
            return null;
        }

        return userId;
    }

    private String encrypt(String encryptContent) {
        return aes.encryptHex(encryptContent);
    }

    private String decrypt(String token) {
        return aes.decryptStr(token);
    }
}
