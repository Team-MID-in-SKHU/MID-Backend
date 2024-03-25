package com.skhu.mid_skhu.global.exception.model;

import com.skhu.mid_skhu.global.exception.ErrorCode;


public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}