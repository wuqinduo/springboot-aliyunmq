package com.wqd.aliyunmq.mq;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.wqd.aliyunmq.config.BusMqConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2018/4/9.
 */
@Component
@ComponentScan
public class MqConsumer implements InitializingBean, DisposableBean{


    @Autowired
    BusMqConfig busMqConfig;

    private Consumer busConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("消费者初始化");
        busConsumer = ONSFactory.createConsumer(busMqConfig.getConsumerProperties());
       // busConsumer.start();
        System.out.println("消费者初始化完成");
    }
    public void start(){
        busConsumer.start();
    }
    public void onMessage(){
        busConsumer.subscribe(busMqConfig.getTopic(), busMqConfig.getSubExpression(), new MessageListener() {
            @Override
            public Action consume(Message message, ConsumeContext context) {
               // System.out.println(JSON.toJSONString(message));
                System.out.println("Receive: " + message);
                System.out.println(new String(message.getBody()));
                return Action.CommitMessage;
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        busConsumer.shutdown();
        System.out.println("停止");
    }

}
