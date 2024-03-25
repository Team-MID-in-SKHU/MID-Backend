package com.skhu.mid_skhu.global.exception.model;

import com.skhu.mid_skhu.global.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
