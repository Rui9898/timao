package com.lr.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    REGISTER_ERROR(0,"注册失败"),
    ORDER_ERROR(1,"订单异常"),
    CART_ERROR(2,"添加购物车异常"),
    NOT_ERROR(3,"未登录"),
    ID_IS_NULL(4,"ID为空"),
    CART_IS_NULL(5,"购物车记录不存在"),
    CART_SAVE_ERROR(6,"购物车保存失败"),
    PRODUCT_NOT_EXIST(7,"商品不存在"),
    STOCK_ERROR(8,"库存不足"),
    STOCK_UPDATE_ERROR(9,"库存更新失败"),
    ORDER_CREATE_FALL(10,"创建订单失败");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
