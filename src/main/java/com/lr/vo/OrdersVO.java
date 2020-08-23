package com.lr.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrdersVO {
    private String loginName;
    private String userAddress;
    private String serialnumber;
    private Float cost;
    private List<OrderDetailVO> orderDetailVOS;
}
