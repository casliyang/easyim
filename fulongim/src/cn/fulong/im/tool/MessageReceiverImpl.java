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
			
			//ƴ���ַ�������action�в��
			String returnmsg = om.getStringProperty("content")+
			"&"+om.getStringProperty("sender")+
			"&"+om.getStringProperty("receiver")+
			"&"+om.getStringProperty("conversationID")+
			"&"+om.getStringProperty("sendTime");
			
			//��������Ϣʱ������Ϣ��ת���������
			//���ö��У��������ַ�����������ת����������Ϣ��������ȡ��Ϣ���ٶȴ��ڽ�����Ϣ�ٶȣ�
			//�Ӷ�������Ϣû�����վ��Ѿ������ǵ����
			ConcurrentHashMap<String,String> messageList = MessageMap.getMessageMap();
			String temprec = om.getStringProperty("receiver");
			String tempsend = om.getStringProperty("sender");
			//�����Ϣ��ת�б����Ѱ���һ��keyΪ����ǰ������&�����ߡ�����Ϣ�������׷����Ϣ������Ϣ�;���Ϣ��|�ָ�
			if (messageList.containsKey(tempsend+"&"+temprec)) {
				String tempmsg = messageList.get(tempsend+"&"+temprec);
				tempmsg += "|"+returnmsg;
				messageList.remove(tempsend+"&"+temprec);
				messageList.put(tempsend+"&"+temprec, tempmsg);
			}else{
				//�����в�����keyΪ����ǰ������&�����ߡ�����Ϣ����ֱ������һ��
				messageList.put(tempsend+"&"+temprec, returnmsg);
			}
			
			//ÿ�յ�һ����Ϣ�����жϻ�ÿ��б����Ƿ��Ѿ������˵�ǰ����Ϣ�ķÿ�
			//��ÿ��б�ṹΪ����receiver��sender1|sender2|sender3|...��
			ConcurrentHashMap<String,String> activeClients = ActiveClientsMap.getActiveClientsMap();
			//�����ÿ���ת�б����Ѱ���һ��keyΪ����ǰ�����ߡ�����Ϣ����׷�ӵ�ǰ��Ϣ�ķ����ߵ�map��value��
			if (activeClients.containsKey(temprec)) {
				String tempclients = activeClients.get(temprec);
				tempclients += "|"+tempsend;
				activeClients.remove(temprec);
				activeClients.put(temprec, tempclients);
			}else{
				//�����в�����keyΪ����ǰ������&�����ߡ�����Ϣ����ֱ������һ��
				activeClients.put(temprec, tempsend);
			}
			
//			this.setRecmsg(returnmsg);
//			this.setReceiver(om.getStringProperty("receiver"));
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}