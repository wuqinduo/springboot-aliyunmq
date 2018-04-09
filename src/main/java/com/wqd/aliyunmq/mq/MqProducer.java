package com.wqd.aliyunmq.mq;

import com.aliyun.openservices.ons.api.*;
import com.wqd.aliyunmq.config.BusMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell on 2018/4/9.
 */
@Component
public class MqProducer implements InitializingBean,DisposableBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqProducer.class);

    @Autowired
    BusMqConfig busMqConfig;

    private Producer producer;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("生产者初始化");
        producer = ONSFactory.createProducer(busMqConfig.getProducerProperties());
        producer.start();
    }

    public void sentMessage(Message message){
            producer.sendAsync(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    LOGGER.info(sendResult.getTopic()+"-----"+sendResult.getMessageId());
                }

                @Override
                public void onException(OnExceptionContext context) {
                    LOGGER.error(context.getTopic()+"-----"+context.getMessageId()+":error="+context.getException());
                }
            });
    }

    @Override
    public void destroy() throws Exception {
            producer.shutdown();
    }
}
