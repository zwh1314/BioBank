package com.example.BioBank.Service.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.BioBank.DTO.UserInfoDTO;
import com.example.BioBank.Dao.BaseOperateLogDao;
import com.example.BioBank.Dao.UserInfoDao;
import com.example.BioBank.Entity.BaseOperateLog;
import com.example.BioBank.Entity.CacheSet;
import com.example.BioBank.Entity.UserInfo;
import com.example.BioBank.Exception.BioBankRuntimeException;
import com.example.BioBank.Response.Response;
import com.example.BioBank.enums.UserPriorityEnum;
import com.example.BioBank.enums.ResponseEnum;
import com.example.BioBank.utils.EmailUtil;
import com.example.BioBank.utils.HTTPRequestUtil;
import com.example.BioBank.utils.TokenUtil;
import com.example.BioBank.DTO.UserDTO;
import com.example.BioBank.Entity.User;
import com.example.BioBank.Dao.UserDao;
import com.example.BioBank.Service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

//    @Autowired
//    private MsgUtil msgUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private BaseOperateLogDao baseOperateLogDao;

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> signUpByTel(String tel, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Response<Boolean> response = new Response<>();

        UserInfoDTO userInfoDTO = userInfoDao.getUserInfoByTel(tel);
        if (userInfoDTO != null) {
            response.setFail(ResponseEnum.TEL_HAS_BEEN_USED);
            return response;
        }

        User user = new User();
        user.setTel(tel);
        user.setPriority(0);

        try {
            String code = RandomStringUtils.randomNumeric(5);
            String userName = "生物样品库工作人员" + code;

            userDao.insertUser(user);

            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getUserId());
            userInfo.setTel(tel);
            userInfo.setUserName(userName);
            userInfo.setPriority(UserPriorityEnum.getPriorityNameByPriority(0));
            userInfoDao.addUserInfo(userInfo);

            //插入变更日志
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            String operateDescription = "用户: " + user.getUserId() + ", 通过手机号: " + user.getTel() + ", 注册";
            baseOperateLog.setOperateDescription(operateDescription);
            baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
        } catch (Exception e) {
            logger.warn("[getModelBankByConditions Search DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        CacheSet.userCache.put(tel, user);
        response.setSuc(true);

        return response;
    }

    @Override
    public Response<Boolean> signIn(String tel, String password, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // TODO 密码加密
        Response<Boolean> response = new Response<>();

        validateErrorFrequency(tel);

        //md5密码加密
        // String encryptPassword = SecureUtil.md5(password);
        // User user = userDao.getUserByUserNameAndPassword(userName, encryptPassword);

        UserDTO userDTO = verifyUserByTelAndPassword(tel, password);
        if (userDTO == null) {
            Integer errorFreqBoxing = CacheSet.userErrorFrequencyCache.getIfPresent(tel);
            int errorFreq;
            if (errorFreqBoxing == null) {
                errorFreq = 0;
            } else {
                errorFreq = errorFreqBoxing;
            }
            CacheSet.userErrorFrequencyCache.put(tel, errorFreq + 1);
            logger.warn("[login User Not Found], tel: {}, password: {}", tel, password);
            response.setFail(ResponseEnum.TEL_OR_PWD_ERROR);
            return response;
        }

        tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);

        response.setSuc(true);
        return response;
    }

    @Override
    public Response<Boolean> signInByTel(String tel, String verifyCode, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        Response<Boolean> response = new Response<>();

        validateErrorFrequency(tel);

        if (validateVerifyCode(tel, verifyCode)) {
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }

        UserDTO userDTO = getUserByTel(tel);
        if (userDTO == null) {
            Integer errorFreqBoxing = CacheSet.userErrorFrequencyCache.getIfPresent(tel);
            int errorFreq;
            if (errorFreqBoxing == null) {
                errorFreq = 0;
            } else {
                errorFreq = errorFreqBoxing;
            }
            CacheSet.userErrorFrequencyCache.put(tel, errorFreq + 1);
            logger.warn("[login User Not Found], tel: {}, verifyCode: {}", tel, verifyCode);
            response.setFail(ResponseEnum.TEL_OR_PWD_ERROR);
            return response;
        }

        tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);

        response.setSuc(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> updatePassword(String tel, String newPassword) {
        Response<Boolean> response = new Response<>();
        validateErrorFrequency(tel);
        UserDTO userDTO = getUserByTel(tel);
        if (userDTO == null) {
            Integer errorFreqBoxing = CacheSet.userErrorFrequencyCache.getIfPresent(tel);
            int errorFreq;
            if (errorFreqBoxing == null) {
                errorFreq = 0;
            } else {
                errorFreq = errorFreqBoxing;
            }
            CacheSet.userErrorFrequencyCache.put(tel, errorFreq + 1);
            logger.warn("[updatePassword User Not Found], tel: {}", tel);
            response.setFail(ResponseEnum.USER_NOT_FOUND);
            return response;
        }

//        String tel_verifycode=verifyCodeCache.getIfPresent(tel);
//        if (StringUtils.isBlank(tel_verifycode)) {
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_INVALID);
//            return response;
//        }
//        else if(!verifyCode.equals(tel_verifycode)){
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
//            return response;
//        }

//        if(!validateVerifyCode(tel,verifyCode)){
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
//            return response;
//        }
        User user = transformUserDTO2User(userDTO);
        try {
            userDao.updatePassword(tel, newPassword);

            //插入变更日志
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            String operateDescription = "用户: " + user.getUserId() + ", 通过手机号: " + user.getTel() + ", 更新密码";
            baseOperateLog.setOperateDescription(operateDescription);
            baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
        } catch (Exception e) {
            logger.warn("[updatePassword Update DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        //更新完数据库后更新缓存
        user.setPassword(newPassword);
        CacheSet.userCache.put(tel, user);
        response.setSuc(true);
        return response;
    }


    //阿里云短信服务验证
//    @Override
//    public Response<Boolean> getVerifyMsgCode(String tel) {
//        Response<Boolean> response = new Response<>();
//        if (StringUtils.isNotBlank(verifyCodeCache.getIfPresent(tel))) {
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_VALID);
//            return response;
//        }
//        String msgCode = msgUtil.sendSignUpMsgCode(tel);
//        verifyCodeCache.put(tel, msgCode);
//        response.setSuc(true);
//
//        return response;
//    }

    @Override
    public Response<Boolean> getVerifyMsgCode(String tel) {
        Response<Boolean> response = new Response<>();
        HTTPRequestUtil httpRequestUtil = new HTTPRequestUtil();
        //设置JSON类型参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhoneNumber", tel);
        jsonObject.put("template", "志愿帮");
        String result = httpRequestUtil.sendPost("https://api2.bmob.cn/1/requestSmsCode", jsonObject);
        jsonObject = JSONObject.parseObject(result);
        result = jsonObject.getString("smsId");
        if (StringUtils.isNotBlank(result))
            response.setSuc(true);
        else
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_VALID);

        return response;
    }

    @Override
    public Response<Boolean> verifyVerification(String tel, String verifyCode) {

        Response<Boolean> response = new Response<>();
//        String tel_verifycode=verifyCodeCache.getIfPresent(tel);
//        if (StringUtils.isBlank(tel_verifycode)) {
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_INVALID);
//            return response;
//        }
//        else if(!verifyCode.equals(tel_verifycode)){
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
//            return response;
//        }
        if (validateVerifyCode(tel, verifyCode)) {
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }
        response.setSuc(true);

        return response;
    }

    @Override
    public Response<Boolean> getMailVerifyMsgCode(String mail) {
        Response<Boolean> response = new Response<>();
        if (StringUtils.isNotBlank(CacheSet.mailVerifyCodeCache.getIfPresent(mail))) {
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_VALID);
            return response;
        }
        String mailCode = emailUtil.sendMail(mail);
        CacheSet.mailVerifyCodeCache.put(mail, mailCode);
        response.setSuc(true);

        return response;
    }

    @Override
    @Transactional(rollbackFor = BioBankRuntimeException.class)
    public Response<Boolean> deleteUserByUserId(long userId, long delete_userId) {
        Response<Boolean> response = new Response<>();
        //判断是否有删除用户的权限
        UserDTO userDTO = userDao.getUserByUserId(userId);
        UserDTO deleteUserDTO = userDao.getUserByUserId(delete_userId);
        if (userDTO.getPriority() < UserPriorityEnum.ADMINISTRATOR.getPriority() || userDTO.getPriority() <= deleteUserDTO.getPriority()) {
            logger.warn("[User's Priority Is Not Enough], AuthorUser's Priority: {}, DeletedUser's Priority: {}",
                    UserPriorityEnum.getPriorityNameByPriority(userDTO.getPriority()), UserPriorityEnum.getPriorityNameByPriority(deleteUserDTO.getPriority()));
            response.setFail(ResponseEnum.USER_PRIORITY_IS_NOT_ENOUGH);
            return response;
        }

        try {
            userDao.deleteByUserId(delete_userId);
            //插入变更日志
            BaseOperateLog baseOperateLog = new BaseOperateLog();
            String operateDescription = "用户: " + userId + ", 删除了用户: " + delete_userId;
            baseOperateLog.setOperateDescription(operateDescription);
            baseOperateLogDao.insertBaseOperateLog(baseOperateLog);
        } catch (Exception e){
            logger.warn("[deleteUserByUserId Operate DataBase Fail]", e);
            throw new BioBankRuntimeException(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        //删除缓存
        CacheSet.userCache.invalidate(userDTO.getTel());
        response.setSuc(true);
        return response;
    }

    public UserDTO getUserByTel(String tel) {
        User user = CacheSet.userCache.getIfPresent(tel);
        // 缓存中不存在则去db取
        if (user == null) {
            return userDao.getUserByTel(tel);
        }
        return transformUser2UserDTO(user);
    }

    public UserDTO verifyUserByTelAndPassword(String tel, String password) {
        User user = CacheSet.userCache.getIfPresent(tel);
        // 缓存中不存在则去db取
        if (user == null) {
            UserDTO userDTO = userDao.getUserByTelAndPassword(tel, password);
            if (userDTO != null) {
                user = transformUserDTO2User(userDTO);
                user.setPassword(password);
                CacheSet.userCache.put(tel, user);
                return userDTO;
            }
        } else if (StringUtils.equals(user.getPassword(), password)) {
            return transformUser2UserDTO(user);
        }
        return null;
    }

    private void validateErrorFrequency(String tel) {
        Integer errFreq = CacheSet.userErrorFrequencyCache.getIfPresent(tel);
        if (errFreq != null && errFreq >= 5) {
            throw new BioBankRuntimeException(ResponseEnum.USER_ERROR_FREQUENCY_LIMIT);
        }
    }

    public UserDTO transformUser2UserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setTel(user.getTel());
        userDTO.setPriority(user.getPriority());
        return userDTO;
    }

    public User transformUserDTO2User(UserDTO userDTO) {
        User user = new User();
        user.setTel(userDTO.getTel());
        user.setUserId(userDTO.getUserId());
        user.setPriority(userDTO.getPriority());
        return user;
    }

    private boolean validateVerifyCode(String tel, String verifyCode) {
        HTTPRequestUtil httpRequestUtil = new HTTPRequestUtil();
        //设置JSON类型参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhoneNumber", tel);
        String result = httpRequestUtil.sendPost("https://api2.bmob.cn/1/verifySmsCode/" + verifyCode, jsonObject);
        jsonObject = JSONObject.parseObject(result);
        result = jsonObject.getString("msg");
        return StringUtils.isBlank(result);
    }
}
