package com.xingyun;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author xingyun
 * @date 2020/12/17
 */
@EnableDubbo
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ProviderApplication.class)
                .run(args);
    }
}
