package com.xingyun;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.*;

/**
 * @author xingyun
 * @date 2020/12/17
 */
@EnableDubbo
@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

    @Autowired
    private ConsumerComponent consumerComponent;

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerApplication.class)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(1000);

        DefaultThreadFactory defaultThreadFactory = new DefaultThreadFactory("timer");

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 32, 2, TimeUnit.SECONDS, blockingQueue,defaultThreadFactory,new ThreadPoolExecutor.AbortPolicy());

        while (true){
            threadPoolExecutor.submit(() -> consumerComponent.invokeA());
            threadPoolExecutor.submit(() -> consumerComponent.invokeB());
            threadPoolExecutor.submit(() -> consumerComponent.invokeC());

        }
    }
}
