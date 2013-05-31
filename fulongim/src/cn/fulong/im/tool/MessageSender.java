package cn.fulong.im.tool;

import org.springframework.jms.core.JmsTemplate;

public interface MessageSender {

	/**
	 * send message
	 */
	public abstract void sendMessage(String msg);

	public abstract void setJmsTemplate(JmsTemplate jmsTemplate);

}