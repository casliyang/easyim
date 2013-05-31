package cn.fulong.im.amqtool;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.pool.PooledConnection;

import cn.fulong.im.model.MessageModel;

public class ReceiveMsgImpl implements ReceiveMsg{
	MessageModel mm = new MessageModel();
	PooledConnection connection = null;
    Session session = null;
    public MessageModel recMsg(String receiver) {
        Destination destination;
        MessageConsumer consumer;

        try {
        	connection = MQPoolUtil.getConn();
            
            //持久化订阅者的id是信息接受者的id,设置该参数后MQ会记住该id,并为该id持久化保存信息
            connection.setClientID(receiver); 
            connection.start();
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);

            //固定的Topic
            //所有消息都往一个Topic里发会不会出问题？？
            destination = session.createTopic("IMTopic");
            //consumer = session.createConsumer(destination,"receiver = 'A'");
            
            /**
             * 持久化订阅！！！！第二个参数是client名
             * messageSelector为消息选择器；noLocal标志默认为false，当设置为true时限制消费者只能接收和自
             * 己相同的连接（Connection）所发布的消息，此标志只适用于主题，不适用于队列.
             * 
             * 此处通过selector指定仅接受发送给receiver的消息
             */
            //consumer = session.createDurableSubscriber((Topic)destination,receiver,"receiver = '"+receiver+"'",false);
            consumer = session.createDurableSubscriber((Topic)destination,receiver);
            
            MessageListener ml = new MessageListener(){ 
            @Override 
           	//设置监听器
           	public void onMessage(Message m) { 
           	  TextMessage textMsg = (TextMessage) m; 
           	  try{
           	   String recmsg = textMsg.getText();
           	   System.out.println("收到消息:" + recmsg);
           	   mm.setContent(recmsg);
           	   mm.setConversationID(textMsg.getStringProperty("conversationID"));
           	   mm.setSender(textMsg.getStringProperty("sender"));
           	   mm.setReceiver(textMsg.getStringProperty("receiver"));
           	   mm.setSendTime(textMsg.getStringProperty("sendTime"));
           	           	   
           	  } catch (JMSException e) { 
           	   e.printStackTrace(); 
           	  } 
           	 } 
           	};           	
            consumer.setMessageListener(ml);
            
            while(mm == null || mm.getContent() == "" || mm.getContent().equals("")) {
    		}
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	/**
            	* everytime we fetched msg successfully, we should close the session and connection,
            	* otherwise,next long pull will report a execption about "xxxx already connected to server"
            	*/   
                if (null != session)
             	   session.close();
                if (null != connection)
             	   connection.close();
            } catch (Throwable ignore) {
            }
        }
        
		return mm;
    }
}

