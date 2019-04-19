package com.zhi.fiction.util.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.alibaba.fastjson.JSON;

/**
 * 生产者
 * 使用@EnableScheduling注解开启定时任务
 */
@Component
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic, Object msg) {
        String message = JSON.toJSONString(msg);
        ListenableFuture future = kafkaTemplate.send(topic, message);
        //future.addCallback(o -> System.out.println("send-消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
    }
}