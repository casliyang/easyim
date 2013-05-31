package cn.fulong.im.amqtool;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MQProducer extends Thread {

    public void run() {

        String topic = "MQ.TEST";
        Session session = null;
        MessageProducer producer = null;
        try {
            session = MQPoolUtil.getConn().createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createTopic(topic);
            
            System.out.println("session info ->" +session);

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage message = session.createTextMessage("hello message!");
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}