package com.software;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.software.entity.OrderVo;
import com.software.util.Response;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class SoftwareTestApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void createOrderTest(){
        String url="http://localhost:8080/order";
        Response<?> response
                = restTemplate.postForObject(url, new OrderVo(5000, "$5000 worth of merchandise"), Response.class);
        String responseJson = "";
        try {
           responseJson=objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String expectJson = "{\n" +
                "  \"code\": 200,\n" +
                "  \"msg\": \"ok\",\n" +
                "  \"data\": {\n" +
                "    \"id\": 5,\n" +
                "    \"orderSn\": \"a8311644-4f75-4ee8-8568-735dcd8cf73c\",\n" +
                "    \"price\": 2000,\n" +
                "    \"description\": \"$2000 worth of merchandise\"\n" +
                "  }\n" +
                "}";
        try {
            JSONAssert.assertEquals(responseJson,expectJson,true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
