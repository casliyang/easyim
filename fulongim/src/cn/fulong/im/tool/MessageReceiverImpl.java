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
			//如果消息中转列表中已包含一条key为当前接收者的消息
			if (messageList.containsKey(temprec)) {
				String tempmsg = messageList.get(temprec);
				tempmsg += "|"+returnmsg;
				messageList.remove(temprec);
				messageList.put(temprec, tempmsg);
			}else{
				messageList.put(temprec, returnmsg);
			}
			
//			this.setRecmsg(returnmsg);
//			this.setReceiver(om.getStringProperty("receiver"));
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}