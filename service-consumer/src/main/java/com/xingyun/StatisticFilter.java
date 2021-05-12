package com.xingyun;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Activate(group = {CommonConstants.CONSUMER})
public class StatisticFilter implements Filter,Runnable {

    private Map<String, List<TimerContainer>> methodSpendTime = new HashMap<>();

    public StatisticFilter(){
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this,5,5, TimeUnit.SECONDS);
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        long start = System.currentTimeMillis();
        Result invoke = invoker.invoke(invocation);
        long spend = System.currentTimeMillis() - start;
        //System.out.println(invocation.getMethodName() + "执行时间 " + spend + " ms");
        List<TimerContainer> timerContainers = methodSpendTime.get(invocation.getMethodName());
        if (timerContainers ==null){
            timerContainers = new ArrayList<>();
            methodSpendTime.put(invocation.getMethodName(),timerContainers);
        }
        timerContainers.add(new TimerContainer(start,spend));
        return invoke;
    }

    @Override
    public void run() {

        for (Map.Entry<String, List<TimerContainer>> stringListEntry : methodSpendTime.entrySet()) {
            String methodName = stringListEntry.getKey();
            List<TimerContainer> timerContainerList = stringListEntry.getValue();

            long oneMinuteAgo = System.currentTimeMillis() - 1000*60;

            List<TimerContainer> collect = timerContainerList.stream().filter(timerContainer -> timerContainer.getTimestamp() >= oneMinuteAgo)
                    .sorted(Comparator.comparing(timerContainer -> timerContainer.getSpendMilSecond())).collect(Collectors.toList());

            int size = collect.size();

            TimerContainer tp90 = collect.get((int) (size * 0.9));
            TimerContainer tp99 = collect.get((int) (size * 0.99));

            System.out.println(methodName + " tp90 ->" + tp90.getSpendMilSecond() + "ms");
            System.out.println(methodName + " tp99 ->" + tp99.getSpendMilSecond() + "ms");


        }
        System.out.println("=============");

    }

    private static class TimerContainer{
        private long timestamp;

        private long spendMilSecond;

        public TimerContainer(long timestamp, long spendMilSecond) {
            this.timestamp = timestamp;
            this.spendMilSecond = spendMilSecond;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getSpendMilSecond() {
            return spendMilSecond;
        }

        public void setSpendMilSecond(long spendMilSecond) {
            this.spendMilSecond = spendMilSecond;
        }
    }
}
