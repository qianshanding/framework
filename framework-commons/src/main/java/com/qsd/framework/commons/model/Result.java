package com.qsd.framework.commons.model;

import com.qsd.framework.commons.exception.ErrorCode;
import lombok.Data;

import java.text.MessageFormat;

@Data
public class Result<T> {

    private String message = "success";
    private int code = 200;
    private T data;

    public Result() {

    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(ErrorCode error, Object... arguments) {
        this.code = error.getCode();
        this.message = MessageFormat.format(error.getMessage(), arguments);
    }

    public void setError(ErrorCode error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public void setError(ErrorCode error, Object... arguments) {
        this.code = error.getCode();
        this.message = MessageFormat.format(error.getMessage(), arguments);
    }

    public boolean isSuccess() {
        return 200 == this.code;
    }
}

