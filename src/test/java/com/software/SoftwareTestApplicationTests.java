package com.software;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.entity.Order;
import com.software.entity.OrderVo;
import com.software.service.OrderService;
import com.software.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@SpringBootTest
@Slf4j
class SoftwareTestApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单测试
     */
    @Test
    void createOrderTest(){
        //创建订单
        String createOrderUrl="http://localhost:8080/order";
        OrderVo orderVo= new OrderVo(5000, "$5000 worth of merchandise");
        Response<?> createOrderResponse
                = restTemplate.postForObject(createOrderUrl,orderVo,Response.class);

        //检查创建的订单信息是否为空
        Assert.notNull(createOrderResponse,"order info must be not null");

        //获取订单id
        Integer id = null;
        if(createOrderResponse.getData() instanceof LinkedHashMap) {
            LinkedHashMap<?,?> map= (LinkedHashMap) createOrderResponse.getData();
            id= (Integer) map.get("id");
        }
        //查询该订单
        String getOrderUrl="http://localhost:8080/order/"+id;
        Response<?> getOrderResponse
                =restTemplate.getForObject(getOrderUrl,Response.class);
        //检查查询订单的信息是否为空
        Assert.notNull(getOrderResponse,"order info must be not null");
        try {
            String expectJson=objectMapper.writeValueAsString(createOrderResponse);
            String getJson=objectMapper.writeValueAsString(getOrderResponse);
            JSONAssert.assertEquals(expectJson,getJson,true);
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询订单测试
     */
    @Test
    void getOrderTest(){
        Long orderId=8L;
        //查询该订单
        String getOrderUrl="http://localhost:8080/order/"+orderId;
        Response<?> getOrderResponse
                =restTemplate.getForObject(getOrderUrl,Response.class);
        //检查查询订单的信息是否为空
        Assert.notNull(getOrderResponse,"order info must be not null");
        String expectJson = "{" +
                "\"code\":200," +
                "\"msg\":\"OK\"," +
                "\"data\":{" +
                    "\"id\":8," +
                    "\"orderSn\":\"e3f1e865-a72c-4973-9455-dfbec40f2291\"," +
                    "\"price\":5000," +
                    "\"description\":\"$5000 worth of merchandise\"," +
                    "\"state\":0," +
                    "\"deliver\":0}}";
        try {
            String getJson=objectMapper.writeValueAsString(getOrderResponse);
            JSONAssert.assertEquals(expectJson,getJson,true);
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有订单测试
     */
    @Test
    void getOrderAllTest(){
        String getOrderUrl="http://localhost:8080/order/all";
        Response<?> getOrderResponse
                =restTemplate.getForObject(getOrderUrl,Response.class);
        int sum=0;
        if(getOrderResponse.getData() instanceof ArrayList) {
            ArrayList<?> list= (ArrayList<?>) getOrderResponse.getData();
            sum=list.size();
        }
        int count = orderService.count();
        try {
            JSONAssert.assertEquals(String.valueOf(sum),String.valueOf(count),true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改订单测试
     */
    @Test
    void modifyOrderTest(){
        //创建订单
        String createOrderUrl="http://localhost:8080/order";
        OrderVo orderVo= new OrderVo(5000, "$5000 worth of merchandise");
        Response<?> createOrderResponse
                = restTemplate.postForObject(createOrderUrl,orderVo,Response.class);

        //检查创建的订单信息是否为空
        Assert.notNull(createOrderResponse,"order info must be not null");

        //获取订单id
        Integer id = null;
        if(createOrderResponse.getData() instanceof LinkedHashMap) {
            LinkedHashMap<?,?> map= (LinkedHashMap) createOrderResponse.getData();
            id= (Integer) map.get("id");
        }
        //查询该订单
        Order orderFirst=orderService.getById(id);
        //修改该订单
        String modifyOrderUrl="http://localhost:8080/order/"+id;
        OrderVo orderVoM= new OrderVo(10000, "$10000 worth of merchandise order modified");
        restTemplate.put(modifyOrderUrl,orderVoM);
        //再次查询该顶顶那
        Order orderLast=orderService.getById(id);
        //对比两次查询的信息
        try {
            orderFirst.setDescription(orderVoM.getDescription());
            orderFirst.setPrice(orderVoM.getPrice());
            String expectJson=objectMapper.writeValueAsString(orderFirst);
            String getJson=objectMapper.writeValueAsString(orderLast);
            JSONAssert.assertEquals(expectJson,getJson,true);
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除订单测试
     */
    @Test
    void deleteOrderTest(){
        long orderId=13L;
        //删除订单
        String deleteOrderUrl="http://localhost:8080/order/"+orderId;
        restTemplate.delete(deleteOrderUrl);
        //再次查询
        Order orderNon= orderService.getById(orderId);
        Assert.isNull(orderNon,"order must be non");
    }

    /**
     * 订单支付测试
     */
    @Test
    void payOrderTest(){

        long orderId=8L;
        //支付订单
        String payOrderUrl="http://localhost:8080/order/"+orderId+"/pay";
        Response<?> response
                = restTemplate.postForObject(payOrderUrl, null, Response.class);
        //再次查询
        Order order= orderService.getById(orderId);
        Assert.notNull(order,"order must be non");
        try {
            String expectJson=objectMapper.writeValueAsString(new Response<>().setCode(200).setMsg("OK"));
            String getJson=objectMapper.writeValueAsString(response);
            JSONAssert.assertEquals(expectJson,getJson,true);
            JSONAssert.assertEquals(order.getDeliver().toString(),"1",true);
            JSONAssert.assertEquals(order.getState().toString(),"1",true);
        } catch (JsonProcessingException | JSONException e) {
            e.printStackTrace();
        }
    }
}
