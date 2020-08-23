package com.lr.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE(1,"男"),
    FEMALE(0,"女");

    @EnumValue
    private Integer code;
    private String msg;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    GenderEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    GenderEnum() {
    }
}
