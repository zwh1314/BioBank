package com.example.BioBank.Exception;

import com.example.BioBank.enums.ResponseEnum;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class BioBankRuntimeException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BioBankRuntimeException.class);

    private int exceptionCode;

    public BioBankRuntimeException(String msg) {
        super(msg);
    }

    public BioBankRuntimeException(int code, String msg) {
        super(msg);
        this.exceptionCode = code;
    }

    public BioBankRuntimeException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }

    public BioBankRuntimeException(ResponseEnum responseEnum, String msg) {
        this(responseEnum.getCode(), responseEnum.getMsg());
    }
}
