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
public class MethodAccessWindow {

    @AllArgsConstructor
    @Data
    class Node{
        long time;
    }

    private static EventPublisher eventPublisher;

    public static void setEventPubisher(ApplicationContext applicationContext ){
        eventPublisher=applicationContext.getBean(EventPublisher.class);
    }

    Queue<Node> queue;
    ScheduledExecutorService scheduledExecutorService;

    private int windowTime;
    private int size;

    private String windowKey;
    private long lastCallTime;
    private long shutDownTime;
//    private static final long SHUT_DOWN_TIME=1000*60*10;

    public MethodAccessWindow(int windowTime, int size, String windowKey){
        queue=new ArrayBlockingQueue<>(size);
        this.windowTime = windowTime;
        this.size=size;
        this.windowKey = windowKey;
        this.lastCallTime=System.currentTimeMillis();
        shutDownTime =windowTime*1000*3;
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
        this.lastCallTime=System.currentTimeMillis();
        if(queue.size()>=size){
            return false;
        }else {
            queue.add(new Node(System.currentTimeMillis()));
            return true;
        }
    }

    public void clean(){
//        System.out.println("clean "+userId);
        for (Node node:queue){
            if (System.currentTimeMillis()-node.getTime()> (windowTime *1000)){
                queue.poll();
            }
        }

//        System.out.println("timeDiff: "+userId +"  "+ (System.currentTimeMillis() - lastCallTime));
        //过10分钟不用则自动销毁
        if (System.currentTimeMillis()-lastCallTime >= shutDownTime){
            log.info("发送event");
            WindowCloseEvent windowCloseEvent=new WindowCloseEvent(this, windowKey);
            eventPublisher.publish(windowCloseEvent);
            log.info("发送event end");

            destroy();
        }
    }

    public void reset(){
        queue.clear();
    }

    public void destroy(){
        log.info("destroy");
        try {
            scheduledExecutorService.shutdown();
            if (!scheduledExecutorService.awaitTermination(60, TimeUnit.SECONDS)) {
                // 超时的时候向线程池中所有的线程发出中断
                scheduledExecutorService.shutdownNow();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            scheduledExecutorService.shutdownNow();
        }
        log.info("end destroy");
    }



}
