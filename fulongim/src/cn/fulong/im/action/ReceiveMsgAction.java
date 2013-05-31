package cn.fulong.im.action;

import java.util.concurrent.ConcurrentHashMap;

import cn.fulong.im.tool.MessageMap;

import com.opensymphony.xwork2.ActionSupport;

public class ReceiveMsgAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	String returnmsg;
	String receiver;
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReturnmsg() {
		return returnmsg;
	}
	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}

	@Override
	public String execute() throws Exception  {
		// TODO Auto-generated method stub
		ConcurrentHashMap<String,String> messageList = MessageMap.getMessageMap();
		while (true) {
			if (messageList.containsKey(receiver)) {
				returnmsg = messageList.get(receiver);
				messageList.remove(receiver);
				break;
				}
			Thread.sleep(1000);
		}
		
		System.out.println("returnmsg is :"+returnmsg);

		return "success";
	}

}
