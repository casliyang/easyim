package cn.fulong.im.action;

import org.springframework.context.ApplicationContext;

import cn.fulong.im.model.MessageModel;
import cn.fulong.im.tool.MessageSender;
import cn.fulong.im.tool.MyContextFactory;

import com.opensymphony.xwork2.ActionSupport;

public class SendMsgAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MessageModel mm;
	public MessageModel getMm() {
		return mm;
	}
	public void setMm(MessageModel mm) {
		this.mm = mm;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub		
		
		ApplicationContext context = MyContextFactory.getContext();
		MessageSender ms=(MessageSender) context.getBean("topicMessageSender");
		
		//将消息拼装成以&分割的字符串，供jmsTemplate处理
		String content = mm.getContent();
		content = java.net.URLDecoder.decode(content,"UTF-8");
		String msg = content+"&"+mm.getSender()+"&"+mm.getReceiver()+"&"+mm.getConversationID()+"&"+mm.getSendTime();
		
//		System.out.println("conversation ID:" + mm.getConversationID());
//		System.out.println("content:"+mm.getContent());
//		System.out.println("sender:"+mm.getSender());
//		System.out.println("receiver:"+mm.getReceiver());
//		System.out.println("sendtime:"+mm.getSendTime());
		
		ms.sendMessage(msg);
		
		return "success";
	}
}
