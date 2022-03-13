package com.example.BioBank.enums;

public enum OperatePriorityEnum {
    MODEL_BANK_ACCESSIBLE(1,"生物样品库可以访问"),
    MODEL_BANK_NOT_ACCESSIBLE(-1,"生物样品库不可以访问"),

    BIO_MODEL_ACCESSIBLE(1,"生物样品可以访问"),
    BIO_MODEL_NOT_ACCESSIBLE(-1,"生物样品不可以访问"),

    UB_RELATION_PRIORITY_0(0,"该用户对该生物样品库没有任何操作权限"),
    UB_RELATION_PRIORITY_1(1,"该用户对该生物样品库只有访问权限"),
    UB_RELATION_PRIORITY_3(3,"该用户对该生物样品库只有访问和修改操作权限"),
    UB_RELATION_PRIORITY_7(7,"该用户对该生物样品库有所有操作权限"),

    UM_RELATION_PRIORITY_0(0,"该用户对该生物样品没有任何操作权限"),
    UM_RELATION_PRIORITY_1(1,"该用户对该生物样品只有访问权限"),
    UM_RELATION_PRIORITY_3(3,"该用户对该生物样品只有访问和修改操作权限"),
    UM_RELATION_PRIORITY_7(7,"该用户对该生物样品有所有操作权限");

    private final int value;
    private final String msg;

    OperatePriorityEnum(int value, String msg){
        this.value = value;
        this.msg = msg;
    }

    public int getValue(){
        return this.value;
    }

    public static String getPriorityMsgByPriority(int priority) {
        for (OperatePriorityEnum operatePriorityEnum : OperatePriorityEnum.values()) {
            if (operatePriorityEnum.value == priority)
                return operatePriorityEnum.msg;
        }
        return null;
    }
}
