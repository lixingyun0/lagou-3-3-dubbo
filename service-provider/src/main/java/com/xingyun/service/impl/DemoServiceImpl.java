package com.xingyun.service.impl;

import com.xingyun.service.IDemoService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements IDemoService {
    @Override
    public String methodA() {

        long sleep = RandomUtils.nextLong(0, 100);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodA sleep "+sleep;
    }

    @Override
    public String methodB() {
        long sleep = RandomUtils.nextLong(0, 100);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodB sleep "+sleep;
    }

    @Override
    public String methodC() {
        long sleep = RandomUtils.nextLong(0, 100);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "methodC sleep "+sleep;
    }
}
