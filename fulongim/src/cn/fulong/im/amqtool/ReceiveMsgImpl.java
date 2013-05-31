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
            
            //�־û������ߵ�id����Ϣ�����ߵ�id,���øò�����MQ���ס��id,��Ϊ��id�־û�������Ϣ
            connection.setClientID(receiver); 
            connection.start();
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);

            //�̶���Topic
            //������Ϣ����һ��Topic�﷢�᲻������⣿��
            destination = session.createTopic("IMTopic");
            //consumer = session.createConsumer(destination,"receiver = 'A'");
            
            /**
             * �־û����ģ��������ڶ���������client��
             * messageSelectorΪ��Ϣѡ������noLocal��־Ĭ��Ϊfalse��������Ϊtrueʱ����������ֻ�ܽ��պ���
             * ����ͬ�����ӣ�Connection������������Ϣ���˱�־ֻ���������⣬�������ڶ���.
             * 
             * �˴�ͨ��selectorָ�������ܷ��͸�receiver����Ϣ
             */
            //consumer = session.createDurableSubscriber((Topic)destination,receiver,"receiver = '"+receiver+"'",false);
            consumer = session.createDurableSubscriber((Topic)destination,receiver);
            
            MessageListener ml = new MessageListener(){ 
            @Override 
           	//���ü�����
           	public void onMessage(Message m) { 
           	  TextMessage textMsg = (TextMessage) m; 
           	  try{
           	   String recmsg = textMsg.getText();
           	   System.out.println("�յ���Ϣ:" + recmsg);
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

