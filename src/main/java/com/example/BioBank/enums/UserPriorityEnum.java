package com.example.BioBank.enums;

import org.apache.commons.lang3.StringUtils;

public enum UserPriorityEnum {
    COMMONUSER(0, "普通用户"), INVALIDUSER(-1, "无效用户"), ADMINISTRATOR(1, "管理员"), SUPERADMINISTRATOR(2, "超级管理员");


    private final int priority;
    private final String priorityName;

    UserPriorityEnum(int priority, String priorityName) {
        this.priority = priority;
        this.priorityName = priorityName;
    }

    public int getPriority(){
        return this.priority;
    }

    public static String getPriorityNameByPriority(int priority) {
        for (UserPriorityEnum userPriorityEnum : UserPriorityEnum.values()) {
            if (userPriorityEnum.priority == priority)
                return userPriorityEnum.priorityName;
        }
        return null;
    }

    public static int getPriorityByPriorityName(String priorityName) {
        for (UserPriorityEnum userPriorityEnum : UserPriorityEnum.values()) {
            if (StringUtils.equals(userPriorityEnum.priorityName, priorityName))
                return userPriorityEnum.priority;
        }
        return -1;
    }
}