package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author wcxstart
 * @date 2021-01-23-11:16
 */
public class JmsProduce_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://39.102.112.207:61616";
    public static final String TOPIC_NAME = "topic-atguigu";

    public static void main(String[] args) throws JMSException {

        System.out.println("***********zs");
        //1、创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过连接工具，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();


        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);

        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //重点：将start到这启动
        connection.start();
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg-persist--->"+i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("******持久化消息发布到MQ完成******");

    }

}
