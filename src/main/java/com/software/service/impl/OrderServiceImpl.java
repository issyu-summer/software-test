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
        //未支付--未发货
        order.setState(0);
        order.setDeliver(0);
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

    @Override
    public Object payOrder(Long id) {
        //检查订单是否存在
        Order order = getById(id);
        if(order==null){
            return new Response<>().setCode(404).setMsg("not found");
        }
        //检查订单状态--未支付、已支付、禁止
        Integer state = order.getState();
        if(state!=0){
            return new Response<>().setCode(502).setMsg("order payed");
        }
        //支付--省略其他操作
        order.setState(1);
        //发货--未发货、已发货---省略其他操作
        order.setDeliver(1);
        updateById(order);
        return new Response<>().setCode(200).setMsg("OK");
    }


}
