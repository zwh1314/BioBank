package com.example.BioBank.aspect;

import com.example.BioBank.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Aspect
@Component
public class TokenAspect {

    @Autowired
    private TokenUtil tokenUtil;

    @Pointcut("within(com.example.BioBank.Controller.*.*) " +
            "&& !execution(* com.example.BioBank.Controller.UserController.*(..))")
    private void verifyToken() {}

    @Before("verifyToken()")
    public Long getVerifyToken() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        HttpServletResponse response = null;
        if (requestAttributes != null) {
            response = requestAttributes.getResponse();
        }

        String token = null;
        if (request != null) {
            token = tokenUtil.getToken(request, response);
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }

        return Long.valueOf(token);
    }

    @Pointcut("execution(public * com.example.BioBank.Controller.UserController.signUp())")
    private void signUp() {}

    @After("signUp()")
    public void generateToken() {

    }
}
