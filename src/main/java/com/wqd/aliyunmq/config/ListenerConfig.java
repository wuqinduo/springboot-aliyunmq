package com.wqd.aliyunmq.config;

import com.wqd.aliyunmq.mq.MqConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2018/4/9.
 */
@Component
public class ListenerConfig implements CommandLineRunner{
    @Autowired
    MqConsumer mqConsumer;
    @Override
    public void run(String... strings) throws Exception {
        System.out.println("开始消费");
        mqConsumer.start();
        mqConsumer.onMessage();
    }
}
