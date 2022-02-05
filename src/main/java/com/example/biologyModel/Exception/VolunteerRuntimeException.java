package com.example.biologyModel.Exception;

import com.example.biologyModel.enums.ResponseEnum;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class VolunteerRuntimeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(VolunteerRuntimeException.class);

    private int exceptionCode;

    public VolunteerRuntimeException(String msg) {
        super(msg);
    }

    public VolunteerRuntimeException(int code, String msg) {
        super(msg);
        this.exceptionCode = code;
    }

    public VolunteerRuntimeException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }

    public VolunteerRuntimeException(ResponseEnum responseEnum, String msg) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }
}
