package com.qsd.framework.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorCode {
    private int code;
    private String message;
}
