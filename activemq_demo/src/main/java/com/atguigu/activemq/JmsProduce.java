package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;

/**
 * @author wcxstart
 * @date 2021-01-02-16:05
 */
public class JmsProduce {

    public static final String ACTIVEMQ_URL = "tcp://39.102.112.207:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1、创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2、通过连接工具，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3、创建会话session
        //两个参数，第一个叫事务，第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4、创建目的地（具体是队列还是主题）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5、创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //6、通过messageProducer生产三条消息发送到MQ的队列界面
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg----->" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("******消息发布到MQ完成******");

    }
}
