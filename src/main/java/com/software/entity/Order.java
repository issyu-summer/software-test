package com.software.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author summer
 * @see <a href=""></a><br/>
 * @since 2021/4/29 23:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("order_sn")
    private String orderSn;
    @TableField("price")
    private Integer price;
    @TableField("description")
    private String description;
    @TableField("state")
    private Integer state;
    @TableField("deliver")
    private Integer deliver;

    public Order(OrderVo orderVo,String orderSn){
        this.orderSn=orderSn;
        this.price= orderVo.getPrice();
        this.description=orderVo.getDescription();
    }
}
