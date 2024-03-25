package com.skhu.mid_skhu.global.exception.model;

import com.skhu.mid_skhu.global.exception.ErrorCode;

public class UnauthorizedException extends CustomException{

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
