package com.lr.exception;

import com.lr.enums.ResultEnum;

public class TianmaoException extends RuntimeException{
    public TianmaoException(ResultEnum resultEnum) {

        super(resultEnum.getMsg());

    }
}
