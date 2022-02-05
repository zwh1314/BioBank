package com.example.biologyModel.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class MsgUtil {
    private static final Logger logger = LoggerFactory.getLogger(MsgUtil.class);

    private static final String REGION_ID = "cn-hangzhou";
    private static final String ACCESS_KEY = "LTAIyhcFfw8FjAjq";
    private static final String SECRET = "o7N7ff5ZdtDlrvpAisMtgCoKhgpv9i";
    private static final String SYS_DOMAIN = "dysmsapi.aliyuncs.com";
    private static final String SYS_VERSION = "2017-05-25";
    private static final String SYS_ACTION = "SendSms";
    private static final String SIGN_NAME = "志愿帮";

    private static final String VERIFY_CODE_TEMPLATE_CODE = "SMS_206737818";
    //private static final String PURCHASE_QUOTATION_TEMPLATE_CODE = "";

    /**
     * 发送验证码短信
     */
    public String sendSignUpMsgCode(String tel) {
        // 生成随机6位验证码
        String verifyCode = "123456";
        Map<String, String> map = Maps.newHashMap();
        map.put("code", verifyCode);
        sendMessage(tel, VERIFY_CODE_TEMPLATE_CODE, map);
        return verifyCode;
    }

    private void sendMessage(String tel, String templateCode, Map<String, String> paramMap) {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, ACCESS_KEY, SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(SYS_DOMAIN);
        request.setSysVersion(SYS_VERSION);
        request.setSysAction(SYS_ACTION);
        request.putQueryParameter("RegionId", REGION_ID);
        request.putQueryParameter("SignName", SIGN_NAME);
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("TemplateCode", templateCode);
        if (MapUtils.isNotEmpty(paramMap)) {
            request.putQueryParameter("TemplateParam", SerialUtil.toJsonStr(paramMap));
        }

        CommonResponse response;
        try {
            response = client.getCommonResponse(request);
            logger.info("[SendMessage], request: {}, response: {}", SerialUtil.toJsonStr(request), SerialUtil.toJsonStr(response));
        } catch (Exception e) {
            logger.error("[SendMessage Ex], request: {}", SerialUtil.toJsonStr(request), e);
        }
    }
}