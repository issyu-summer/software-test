package com.software.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software.entity.Order;
import com.software.entity.OrderVo;

/**
 * @author summer
 * @see <a href=""></a><br/>
 * @since 2021/4/29 23:14
 */

public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @return create order response
     * @param orderVo order info
     */
    Object createOrder(OrderVo orderVo);

    /**
     * 删除订单
     * @return delete order response
     * @param id one order id
     */
    Object deleteOrder(Long id);

    /**
     * 修改订单
     * @return modify order response
     * @param id one order id
     * @param orderVo order info
     */
    Object modifyOrder(Long id, OrderVo orderVo);

    /**
     * 查询一个订单
     * @return get order response
     * @param id one order id
     */
    Object getOrder(Long id);

    /**
     * 查询所有订单
     * @return get orders response
     */
    Object getOrders();

    /**
     * 支付订单
     * @param id order id
     * @return pay order response
     */
    Object payOrder(Long id);
}
