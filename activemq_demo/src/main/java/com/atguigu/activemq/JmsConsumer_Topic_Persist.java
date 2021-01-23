package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author wcxstart
 * @date 2021-01-23-11:23
 */
public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://39.102.112.207:61616";
    public static final String TOPIC_NAME = "topic-atguigu";
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("**********zs");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("zs");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark.....");

        connection.start();
        Message message = topicSubscriber.receive();
        while (null != message) {
            if (null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("收到的持久化订阅消息: " + textMessage.getText());
                    message = topicSubscriber.receive(1000L);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        session.close();
        connection.close();

        /**
         * 一定要先运行一次消费者,类似于像MQ注册,我订阅了这个主题
         * 然后再运行主题生产者
         * 无论消费着是否在线,都会接收到,在线的立即接收到,不在线的等下次上线把没接收到的接收
         */
    }
}
