package com.cn.hydra.aspectdemo.rule.util;

import com.cn.hydra.aspectdemo.rule.event.WindowCloseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @program: micro-mall
 * @author: Hydra
 * @create: 2020-11-06 11:25
 **/
@Slf4j
public class MethodAccessWindowSimple {

    @AllArgsConstructor
    @Data
    class Node{
        long time;
    }

    Queue<Node> queue;
    ScheduledExecutorService scheduledExecutorService;

    private int windowTime;
    private int size;

    public MethodAccessWindowSimple(int windowTime, int size){
        queue=new ArrayBlockingQueue<>(size);
        this.windowTime = windowTime;
        this.size=size;
        init();
    }

    private void init(){
        System.out.println("初始化定时任务");
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        scheduledExecutorService.scheduleWithFixedDelay(()->{
            clean();
        },windowTime*1000,1000, TimeUnit.MILLISECONDS);
    }

    public boolean canReceive(){
        if(queue.size()>=size){
            return false;
        }else {
            queue.add(new Node(System.currentTimeMillis()));
            return true;
        }
    }

    public void clean(){
        for (Node node:queue){
            if (System.currentTimeMillis()-node.getTime()> (windowTime *1000)){
                queue.poll();
            }
        }
    }

    public void destroy(){
        try {
            scheduledExecutorService.shutdown();
            if (!scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("awaitTermination interrupted: " + e);
            scheduledExecutorService.shutdownNow();
        }
        System.out.println("end destroy");
    }

}
