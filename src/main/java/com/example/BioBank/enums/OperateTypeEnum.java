package com.example.BioBank.enums;

public enum OperateTypeEnum {
    ADD(1,"新增"),
    REMOVE(2,"移除"),
    MODIFY(3,"改动"),
    SEARCH(4,"查找");

    private final int code;
    private final String msg;

    OperateTypeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){return this.msg;}
}
