package com.xingyun;

import com.xingyun.service.IDemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class ConsumerComponent {

    @DubboReference
    private IDemoService demoService;

    public void invokeA(){
        String s = demoService.methodA();
        //System.out.println(s);
    }

    public void invokeB(){
        String s = demoService.methodB();
        //System.out.println(s);
    }

    public void invokeC(){
        String s = demoService.methodC();
        //System.out.println(s);
    }
}
