package org.test.restAPI.controller.filter.exception;

import lombok.Data;

public class AccessTokenException extends RuntimeException{

    private String msg;

    public AccessTokenException(String msg){
        super(msg);
        this.msg = msg;
    }

}
