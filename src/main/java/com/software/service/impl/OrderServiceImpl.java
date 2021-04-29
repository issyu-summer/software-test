package com.software.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.entity.Order;
import com.software.util.Response;
import org.springframework.stereotype.Service;
import com.software.entity.OrderVo;
import com.software.mapper.OrderMapper;
import com.software.service.OrderService;

import java.util.List;
import java.util.UUID;

/**
 * @author summer
 * @see <a href=""></a><br/>
 * @since 2021/4/29 23:14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Override
    public Object createOrder(OrderVo orderVo) {
        String orderSn= UUID.randomUUID().toString();
        Order order=new Order(orderVo,orderSn);
        if(this.save(order)) {
            return new Response<>().setCode(200).setMsg("ok").setData(order);
        }else {
            return new Response<>().setCode(500).setMsg("server internal error");
        }
    }

    @Override
    public Object deleteOrder(Long id) {
        Order order = getById(id);
        if(order==null){
            return new Response<>().setCode(404).setMsg("not found");
        }
        if(removeById(id)){
            return new Response<>().setCode(200).setMsg("OK");
        }else {
            return new Response<>().setCode(500).setMsg("server internal error");
        }
    }

    @Override
    public Object modifyOrder(Long id, OrderVo orderVo) {
        Order order = getById(id);
        if(order==null){
            return new Response<>().setCode(404).setMsg("not found");
        }
        Order updateOrder
                =new Order(orderVo,order.getOrderSn()).setId(id);
        if(updateById(updateOrder)){
            return new Response<>().setCode(200).setMsg("OK");
        }else {
            return new Response<>().setCode(500).setMsg("server internal error");
        }
    }

    @Override
    public Object getOrder(Long id) {
        Order order = getById(id);
        if(order==null){
            return new Response<>().setCode(404).setMsg("not found");
        }
        return new Response<>().setCode(200).setMsg("OK").setData(order);
    }

    @Override
    public Object getOrders() {
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        List<Order> list = list(queryWrapper);
        if(list.isEmpty()){
            return new Response<>().setCode(404).setMsg("not found");
        }
        return new Response<>().setCode(200).setMsg("OK").setData(list);
    }
}
