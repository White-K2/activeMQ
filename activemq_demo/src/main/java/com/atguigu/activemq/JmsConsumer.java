package com.atguigu.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;
import java.io.IOException;

/**
 * @author wcxstart
 * @date 2021-01-02-16:50
 */
public class JmsConsumer {

    public static final String ACTIVEMQ_URL = "tcp://39.102.112.207:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("我是消费者2");

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
        //5、创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);
        /*while (true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if(null != textMessage){
                System.out.println("消费者收到消息"+textMessage.getText());
            }else{
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/
        messageConsumer.setMessageListener((message) -> {
            if(null != message&& message instanceof TextMessage) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("消费者收到消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
