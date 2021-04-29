package com.software;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author summer
 */
@SpringBootApplication(scanBasePackages = {"com.software"})
@MapperScan("com.software.mapper")
public class SoftwareTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareTestApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

}
