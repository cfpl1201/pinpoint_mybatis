package com.dbq;

import com.dbq.common.redis.RedisBasicConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableEurekaClient
@Import({RedisBasicConfig.class})
@EnableFeignClients({"com.dbq.common.export.order"})
@MapperScan("com.dbq.mapper")
@SpringBootApplication(scanBasePackages = {"com.dbq", "com.dbq.common.export.order"})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
