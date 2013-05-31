package cn.fulong.im.tool;

import org.springframework.jms.core.JmsTemplate;

/**
 * message sender
 * @description <p></p>
 * @author quzishen
 * @project NormandyPositionII
 * @class MessageSender.java
 * @version 1.0
 * @time 2011-1-11
 */
public class MessageSenderImpl implements MessageSender {
	public JmsTemplate jmsTemplate;
	
	/* (non-Javadoc)
	 * @see cn.fulong.im.tool.MessageSender#sendMessage(java.lang.String)
	 */
	@Override
	public void sendMessage(String msg){
		//在MessageConverter中拆分消息字符串，填充各属性
		jmsTemplate.convertAndSend(msg);
	}
	/* (non-Javadoc)
	 * @see cn.fulong.im.tool.MessageSender#setJmsTemplate(org.springframework.jms.core.JmsTemplate)
	 */
	@Override
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}