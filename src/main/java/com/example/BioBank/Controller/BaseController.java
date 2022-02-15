package com.example.BioBank.Controller;

import com.example.BioBank.aspect.TokenAspect;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class BaseController {

    @Autowired
    private TokenAspect tokenAspect;

    private static final String TEL_FORMAT = "^[1][0-9]{10}$";

    private static final String MAIL_FORMAT = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    protected Long getUserId() {
        Long userId = tokenAspect.getVerifyToken();
        validateUserId(userId);
        return userId;
    }

    /**
     * 验证用户手机号格式
     */
    protected void validateUserTel(String tel) {
        Validate.isTrue(StringUtils.isNotBlank(tel));
        Validate.isTrue(tel.length() == 11 && NumberUtils.isDigits(tel));
        Validate.isTrue(tel.matches(TEL_FORMAT));
    }

    /**
     * 验证用户邮箱格式
     */
    protected void validateUserMail(String mail) {
        Validate.isTrue(StringUtils.isNotBlank(mail));
        Validate.isTrue(mail.matches(MAIL_FORMAT));
    }


    /**
     * 验证用户手机号格式
     */
    protected void validateUserTel(List<String> tels) {
        for (String tel : tels) {
            Validate.isTrue(StringUtils.isNotBlank(tel));
            Validate.isTrue(tel.length() == 11 && NumberUtils.isDigits(tel));
            Validate.isTrue(tel.matches(TEL_FORMAT));
        }
    }

    /**
     * 验证用户密码格式
     */
    protected void validateUserPassword(String password) {
        Validate.isTrue(StringUtils.isNotBlank(password), "密码不能为空");
        Validate.isTrue(password.length() >= 8 && password.length() <= 20, "密码长度大于8且小于20");
    }

    /**
     * 验证短信验证码格式
     */
    protected void validateVerifyMsgCode(String msgCode) {
        Validate.isTrue(StringUtils.isNotBlank(msgCode));
        Validate.isTrue(msgCode.length() == 6 && NumberUtils.isDigits(msgCode));
    }

    /**
     * 验证用户id
     */
    protected void validateUserId(long userId) {
        Validate.isTrue(userId > 0);
    }

    /**
     * 验证分页参数
     */
    protected void validatePageParameter(int pageNo, int pageSize) {
        Validate.isTrue(pageNo >= 0 && pageSize > 0);
    }
}
