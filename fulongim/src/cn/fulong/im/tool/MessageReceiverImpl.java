package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * message receiver
 * @description <p></p>
 * @author quzishen
 * @project NormandyPositionII
 * @class MessageReceiver.java
 * @version 1.0
 * @time 2011-1-11
 */
public class MessageReceiverImpl implements MessageListener, MessageReceiver {
	
	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	/* (non-Javadoc)
	 * @see cn.fulong.im.tool.MessageReceiver#onMessage(javax.jms.Message)
	 */
	private String recmsg = "";
	private String receiver = "";
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRecmsg() {
		return recmsg;
	}

	public void setRecmsg(String recmsg) {
		this.recmsg = recmsg;
	}


	@Override
	public void onMessage(Message m) {
		
		ObjectMessage om = (ObjectMessage) m;
		try {
//			System.out.println("content:"+om.getStringProperty("content"));
//			System.out.println("model:"+om.getJMSDeliveryMode());
//			System.out.println("destination:"+om.getJMSDestination());
//			System.out.println("type:"+om.getJMSType());
//			System.out.println("messageId:"+om.getJMSMessageID());
//			System.out.println("time:"+om.getJMSTimestamp());
//			System.out.println("expiredTime:"+om.getJMSExpiration());
//			System.out.println("priority:"+om.getJMSPriority());
//			System.out.println("---------------------------");
			
			//拼接字符串，到action中拆分
			String returnmsg = om.getStringProperty("content")+
			"&"+om.getStringProperty("sender")+
			"&"+om.getStringProperty("receiver")+
			"&"+om.getStringProperty("conversationID")+
			"&"+om.getStringProperty("sendTime");
			
			//监听到消息时，往消息中转队列中添加
			//采用队列，而不是字符串变量做中转，避免了消息监听器获取消息的速度大于接收消息速度，
			//从而导致消息没被接收就已经被覆盖的情况
			ConcurrentHashMap<String,String> messageList = MessageMap.getMessageMap();
			String temprec = om.getStringProperty("receiver");
			String tempsend = om.getStringProperty("sender");
			//如果消息中转列表中已包含一条key为“当前发送者&接收者”的消息，则继续追加消息，新消息和旧消息用|分割
			if (messageList.containsKey(tempsend+"&"+temprec)) {
				String tempmsg = messageList.get(tempsend+"&"+temprec);
				tempmsg += "|"+returnmsg;
				messageList.remove(tempsend+"&"+temprec);
				messageList.put(tempsend+"&"+temprec, tempmsg);
			}else{
				//队列中不包含key为“当前发送者&接收者”的消息，就直接新增一条
				messageList.put(tempsend+"&"+temprec, returnmsg);
			}
			
			//每收到一条消息，就判断活动访客列表中是否已经包含了当前发消息的访客
			//活动访客列表结构为：【receiver，sender1|sender2|sender3|...】
			ConcurrentHashMap<String,String> activeClients = ActiveClientsMap.getActiveClientsMap();
			//如果活动访客中转列表中已包含一条key为“当前接受者”的消息，就追加当前信息的发送者到map的value中
			if (activeClients.containsKey(temprec)) {
				String tempclients = activeClients.get(temprec);
				tempclients += "|"+tempsend;
				activeClients.remove(temprec);
				activeClients.put(temprec, tempclients);
			}else{
				//队列中不包含key为“当前发送者&接收者”的消息，就直接新增一条
				activeClients.put(temprec, tempsend);
			}
			
//			this.setRecmsg(returnmsg);
//			this.setReceiver(om.getStringProperty("receiver"));
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}