package com.lr.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mr.li
 * @since 2020-07-25
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Cart implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer productId;

    private Integer quantity;

    private Float cost;

    private Integer userId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

  public Cart(Integer productId, Integer quantity, Float cost, Integer userId) {
    this.productId = productId;
    this.quantity = quantity;
    this.cost = cost;
    this.userId = userId;
  }
}
