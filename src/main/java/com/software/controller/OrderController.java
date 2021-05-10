package com.software.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.software.entity.OrderVo;
import com.software.service.OrderService;

/**
 * @author summer
 * @see <a href=""></a><br/>
 * @since 2021/4/29 23:13
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping
    public Object createOrder(@RequestBody OrderVo orderVo){
        return orderService.createOrder(orderVo);
    }

    /**
     * 删除订单
     */
    @DeleteMapping("{id}")
    public Object deleteOrder(@PathVariable Long id){
        return orderService.deleteOrder(id);
    }


    /**
     *修改订单
     */
    @PutMapping("{id}")
    public Object modifyOrder(@PathVariable Long id, @RequestBody OrderVo orderVo){
        return orderService.modifyOrder(id,orderVo);
    }

    /**
     * 查询单个订单
     */
    @GetMapping("{id}")
    public Object getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }

    /**
     * 查询全部订单
     */
    @GetMapping("/all")
    public Object getOrders(){
        return orderService.getOrders();
    }

    /**
     * 订单支付
     */
    @PostMapping("/{id}/pay")
    public Object payOrder(@PathVariable Long id){
        return orderService.payOrder(id);
    }
}
