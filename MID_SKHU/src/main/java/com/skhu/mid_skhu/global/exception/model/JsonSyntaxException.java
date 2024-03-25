package com.skhu.mid_skhu.global.exception.model;

import com.skhu.mid_skhu.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class JsonSyntaxException extends CustomException{

    public JsonSyntaxException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
