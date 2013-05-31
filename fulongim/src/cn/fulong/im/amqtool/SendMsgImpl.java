package cn.fulong.im.amqtool;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import cn.fulong.im.model.MessageModel;

public class SendMsgImpl implements SendMsg {
    public void msgSend(MessageModel mm) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;
        Destination des;
        MessageProducer producer;
        try {
            //参数为用户名，密码，MQ的url
            connectionFactory = new ActiveMQConnectionFactory(
            		"imuser",
                    "fulong",
                    "tcp://localhost:61616");
            PooledConnectionFactory pcf = new PooledConnectionFactory(); 
            pcf.setConnectionFactory(connectionFactory); 
            //通过连接池创建连接
            connection = pcf.createConnection();
            
            connection.start();
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            des = session.createTopic("IMTopic");
            // 得到消息生成者【发送者】
            producer = session.createProducer(des);
            // ********设置是否持久化
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            //send message
            TextMessage message = session.createTextMessage(mm.getContent());
            message.setStringProperty("conversationID", mm.getConversationID());
            message.setStringProperty("sender", mm.getSender());
            message.setStringProperty("receiver", mm.getReceiver());
            message.setStringProperty("sendTime", mm.getSendTime());
            producer.send(message);
            
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != session)
             	   session.close();
                if (null != connection)
             	   connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
}