package com.example.volunteer.Service.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.volunteer.Dao.UserInfoDao;
import com.example.volunteer.Entity.UserInfo;
import com.example.volunteer.Exception.VolunteerRuntimeException;
import com.example.volunteer.Response.Response;
import com.example.volunteer.enums.ResponseEnum;
import com.example.volunteer.utils.EmailUtil;
import com.example.volunteer.utils.HTTPRequestUtil;
import com.example.volunteer.utils.MsgUtil;
import com.example.volunteer.utils.TokenUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.example.volunteer.DTO.UserDTO;
import com.example.volunteer.Entity.User;
import com.example.volunteer.Dao.UserDao;
import com.example.volunteer.Service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private Cache<String, String> verifyCodeCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .initialCapacity(5)
            .maximumSize(25)
            .build();

    private Cache<String, String> mailVerifyCodeCache = Caffeine.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .initialCapacity(5)
            .maximumSize(25)
            .build();

    private Cache<String, User> userCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .initialCapacity(10)
            .maximumSize(100)
            .build();

    private Cache<String, Integer> userErrorFrequencyCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .initialCapacity(10)
            .maximumSize(100)
            .build();

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

    @Override
    public Response<Boolean> signUp(String tel, String mail, String userName, String password, String verifyCode) {
        Response<Boolean> response=new Response<>();

        UserDTO userDTO=getUserByTel(tel);
        if(userDTO != null){
            response.setFail(ResponseEnum.TEL_HAS_BEEN_USED);
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
        if(!validateVerifyCode(tel,verifyCode)){
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }

        User user=new User();
        user.setTel(tel);
        user.setPassword(password);
        user.setUserName(userName);
        user.setPriority("普通用户");
        user.setMailAddress(mail);
        boolean result = userDao.insertUser(user) > 0;
        if (result) {
            response.setSuc(true);
        } else {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        return response;
    }
    @Override
    public Response<UserDTO> signUpByTel(String tel,HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        Response<UserDTO> response=new Response<>();

        UserDTO userDTO=getUserByTel(tel);
        if(userDTO != null){
            response.setSuc(userDTO);
            tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);
            return response;
        }

       /* if(!validateVerifyCode(tel,verifyCode)){
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }*/

        String code = RandomStringUtils.randomNumeric(5);
        String userName = "志愿者" + code;
        String code2 = RandomStringUtils.randomNumeric(10);

        User user=new User();
        user.setTel(tel);
        user.setUserName(userName);
        user.setPriority("普通用户");
        user.setPassword(code2);
        boolean result = userDao.insertUser(user) > 0;


        UserInfo userInfo =  new UserInfo();
        userInfo.setTel(tel);
        userInfo.setUserName(userName);
        userInfo.setPriority("普通用户");
        boolean result2 = userInfoDao.addUserInfo(userInfo) > 0;

        if (result && result2) {
            userCache.put(tel,user);
            response.setSuc(transformUser2UserDTO(user));
        } else {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        tokenUtil.generateUserToken(userInfo.getUserId(), servletRequest, servletResponse);

        return response;
    }



    @Override
    public Response<Boolean> signUpByMail(String mail, String userName, String tel,String password, String verifyCode) {
        Response<Boolean> response=new Response<>();

        UserDTO userDTO= getUserByMail(mail);
        if(userDTO != null){
            response.setFail(ResponseEnum.TEL_HAS_BEEN_USED);
            return response;
        }


        String email_verifycode=mailVerifyCodeCache.getIfPresent(mail);

        if (StringUtils.isBlank(email_verifycode)) {
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_INVALID);
            return response;
        }
        else if(!verifyCode.equals(email_verifycode)){
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }

        User user=new User();
        user.setMailAddress(mail);
        user.setPassword(password);
        user.setUserName(userName);
        user.setTel(tel);
        user.setPriority("普通用户");
        boolean result = userDao.insertUser(user) > 0;
        if (result) {
            response.setSuc(true);
        } else {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        return response;
    }


    @Override
    public Response<UserDTO> signIn(String tel, String password, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // TODO 密码加密
        Response<UserDTO> response=new Response<>();

        validateErrorFrequency(tel);

        //md5密码加密
        // String encryptPassword = SecureUtil.md5(password);
        // User user = userDao.getUserByUserNameAndPassword(userName, encryptPassword);

        UserDTO userDTO = verifyUserByTelAndPassword(tel,password);
        if (userDTO == null) {
            int errorFreq = userErrorFrequencyCache.getIfPresent(tel) == null ? 0 : userErrorFrequencyCache.getIfPresent(tel);
            userErrorFrequencyCache.put(tel, errorFreq + 1);
            logger.warn("[login User Not Found], tel: {}, password: {}", tel, password);
            response.setFail(ResponseEnum.TEL_OR_PWD_ERROR);
            return response;
        }

        tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);

        response.setSuc(userDTO);
        return response;
    }

    @Override
    public Response<UserDTO> signInByTel(String tel, String verifyCode, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

        Response<UserDTO> response=new Response<>();

        validateErrorFrequency(tel);

        if(!validateVerifyCode(tel,verifyCode)){
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }

        UserDTO userDTO=getUserByTel(tel);
        if (userDTO == null) {
            int errorFreq = userErrorFrequencyCache.getIfPresent(tel) == null ? 0 : userErrorFrequencyCache.getIfPresent(tel);
            userErrorFrequencyCache.put(tel, errorFreq + 1);
            logger.warn("[login User Not Found], tel: {}, verifyCode: {}", tel, verifyCode);
            response.setFail(ResponseEnum.TEL_OR_PWD_ERROR);
            return response;
        }

        tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);

        response.setSuc(userDTO);
        return response;
    }

    @Override
    public Response<UserDTO> signInByMail(String mail, String password, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        // TODO 密码加密
        Response<UserDTO> response=new Response<>();

        validateErrorFrequency(mail);

        //md5密码加密
        // String encryptPassword = SecureUtil.md5(password);
        // User user = userDao.getUserByUserNameAndPassword(userName, encryptPassword);

        UserDTO userDTO = verifyUserByMailAndPassword(mail,password);
        if (userDTO == null) {
            int errorFreq = userErrorFrequencyCache.getIfPresent(mail) == null ? 0 : userErrorFrequencyCache.getIfPresent(mail);
            userErrorFrequencyCache.put(mail, errorFreq + 1);
            logger.warn("[login User Not Found], mail: {}, password: {}", mail, password);
            response.setFail(ResponseEnum.TEL_OR_PWD_ERROR);
            return response;
        }

        tokenUtil.generateUserToken(userDTO.getUserId(), servletRequest, servletResponse);

        response.setSuc(userDTO);
        return response;
    }

    @Override
    public Response<Boolean> updatePassword(String tel,String newPassword) {
        Response<Boolean> response = new Response<>();
        validateErrorFrequency(tel);
        UserDTO userDTO = getUserByTel(tel);
        if (userDTO == null) {
            int errorFreq = userErrorFrequencyCache.getIfPresent(tel) == null ? 0 : userErrorFrequencyCache.getIfPresent(tel);
            userErrorFrequencyCache.put(tel, errorFreq + 1);
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

        boolean result = userDao.updatePassword(tel, newPassword) > 0;
        if (result) {
            response.setSuc(true);
        } else {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

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
        jsonObject.put("mobilePhoneNumber",tel);
        jsonObject.put("template","志愿帮");
        String result = httpRequestUtil.sendPost("https://api2.bmob.cn/1/requestSmsCode",jsonObject);
        jsonObject = JSONObject.parseObject(result);
        result = jsonObject.getString("smsId");
        if(StringUtils.isNotBlank(result))
            response.setSuc(true);
        else
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_VALID);

        return response;
    }


    /**
     * 验证验证码
     * @param tel
     * @param verifyCode
     * @return
     */
    @Override
    public Response<Boolean> verifyVerification(String tel, String verifyCode) {

        Response <Boolean> response = new Response<>();
        String tel_verifycode=verifyCodeCache.getIfPresent(tel);
//        if (StringUtils.isBlank(tel_verifycode)) {
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_INVALID);
//            return response;
//        }
//        else if(!verifyCode.equals(tel_verifycode)){
//            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
//            return response;
//        }
        if(!validateVerifyCode(tel,verifyCode)){
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_ERROR);
            return response;
        }
        response.setSuc(true);

        return response;
    }

    @Override
    public Response<Boolean> getMailVerifyMsgCode(String mail) {
        Response<Boolean> response = new Response<>();
        if (StringUtils.isNotBlank(mailVerifyCodeCache.getIfPresent(mail))) {
            response.setFail(ResponseEnum.VERIFY_MSG_CODE_VALID);
            return response;
        }
        String mailCode = emailUtil.sendMail(mail);
        mailVerifyCodeCache.put(mail, mailCode);
        response.setSuc(true);

        return response;
    }

    @Override
    public Response<Boolean> deleteUserByUserId(long userId){
        Response<Boolean> response=new Response<>();
        UserDTO userDTO=userDao.getUserByUserId(userId);
        if (userDTO == null) {
            response.setFail(ResponseEnum.USER_NOT_FOUND);
        }
        boolean result = userDao.deleteByUserId(userId) > 0;
        if (result) {
            response.setSuc(true);
        } else {
            response.setFail(ResponseEnum.OPERATE_DATABASE_FAIL);
        }

        return response;
    }

    public UserDTO getUserByTel(String tel) {
        User user = userCache.getIfPresent(tel);
        // 缓存中不存在则去db取
        if (user == null) {
            return userDao.getUserByTel(tel);
        }
        return transformUser2UserDTO(user);
    }

    public UserDTO getUserByMail(String mail) {
        User user = userCache.getIfPresent(mail);
        // 缓存中不存在则去db取
        if (user == null) {
            return userDao.getUserByMail(mail);
        }
        return transformUser2UserDTO(user);
    }

    public UserDTO verifyUserByTelAndPassword(String tel,String password) {
        User user = userCache.getIfPresent(tel);
        // 缓存中不存在则去db取
        if (user == null) {
            UserDTO userDTO = userDao.getUserByTelAndPassword(tel,password);
            if(userDTO != null) {
                user=transformUserDTO2User(userDTO);
                userCache.put(tel,user);
                return userDTO;
            }
        }
        else if(user.getPassword().equals(password)){
            return transformUser2UserDTO(user);
        }
        return null;
    }

    public UserDTO verifyUserByMailAndPassword(String mail,String password) {
        User user = userCache.getIfPresent(mail);
        // 缓存中不存在则去db取
        if (user == null) {
            UserDTO userDTO = userDao.getUserByMailAndPassword(mail,password);
            if(userDTO != null) {
                user=transformUserDTO2User(userDTO);
                userCache.put(mail,user);
                return userDTO;
            }
        }
        else if(user.getPassword().equals(password)){
            return transformUser2UserDTO(user);
        }
        return null;
    }

    private void validateErrorFrequency(String tel) {
        Integer errFreq = userErrorFrequencyCache.getIfPresent(tel);
        if (errFreq != null && errFreq >= 5) {
            throw new VolunteerRuntimeException(ResponseEnum.USER_ERROR_FREQUENCY_LIMIT);
        }
    }

    public UserDTO transformUser2UserDTO(User user){
        UserDTO userDTO=new UserDTO();
        userDTO.setTel(user.getTel());
        userDTO.setMailAddress(user.getMailAddress());
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPriority(user.getPriority());
        return userDTO;
    }

    public User transformUserDTO2User(UserDTO userDTO){
        User user=new User();
        user.setTel(userDTO.getTel());
        user.setMailAddress(userDTO.getMailAddress());
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setPriority(userDTO.getPriority());
        return user;
    }

    private boolean validateVerifyCode(String tel, String verifyCode){
        HTTPRequestUtil httpRequestUtil = new HTTPRequestUtil();
        //设置JSON类型参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mobilePhoneNumber",tel);
        String result = httpRequestUtil.sendPost("https://api2.bmob.cn/1/verifySmsCode/"+verifyCode,jsonObject);
        jsonObject = JSONObject.parseObject(result);
        result = jsonObject.getString("msg");
        if(StringUtils.isBlank(result))
            return false;
        else
            return true;
    }
}
