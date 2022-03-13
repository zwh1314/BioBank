package com.example.BioBank.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(200, "成功"),

    FAIL(1, "失败"),

    ILLEGAL_PARAM(101, "参数错误"),
    TEL_OR_PWD_ERROR(102, "手机号或密码错误"),
    VERIFY_MSG_CODE_INVALID(103, "短信验证码已失效，请重试"),
    VERIFY_MSG_CODE_VALID(104,"短信验证码已发送，请查收"),
    USER_NOT_FOUND(105, "未找到对应的用户"),
    USERINFO_NOT_FOUND(106,"未找到对应的用户信息"),
    USER_NOT_SIGN_IN(107,"用户未登录"),

    OBJECT_RELATIVE_TEXT_NOT_FOUND(108,"未找到相关内容的信息"),
    OBJECT_IN_ONE_WEEK_NOT_FOUND(109,"未找到近一周的信息"),

    OPERATE_CACHE_FAIL(114,"缓存操作失败"),
    OPERATE_DATABASE_FAIL(115,"数据库操作失败"),
    TEL_HAS_BEEN_USED(116,"该手机号已注册"),
    VERIFY_MSG_CODE_ERROR(117,"验证码错误"),
    USER_ERROR_FREQUENCY_LIMIT(118,"用户尝试次数已达上限，请5分钟后再试"),
    UPLOAD_OSS_FAILURE(119,"文件上传OSS失败"),

    INSUFFICIENT_CREDIT(122,"积分不足"),
    NUM_NOT_ENOUGH(123,"库存不足"),
    OBJECT_RELATED_NOT_FOUND(124,"相关对象不存在"),

    BIO_MODEL_NOT_ACCESSIBLE(130,"生物样品目前不可以访问"),
    MODEL_BANK_NOT_ACCESSIBLE(131,"生物样品库目前不可以访问"),

    USER_PRIORITY_IS_NOT_ENOUGH(220, "用户的权限不够"),
    OPERATE_REMOVE_PRIORITY_ONLY_BELONGS_TO_THE_OWNER(221,"对生物样本（库）的移除权限仅属于本样品（库）的拥有者，请重新选择待赋予的权限"),

    BASE_OPERATE_LOG_SEARCH_NOT_FOUND(125,"查找的基本操作日志记录不存在"),

    SERVER_ERROR(500, "服务器内部错误");

    private final int code;
    private final String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
