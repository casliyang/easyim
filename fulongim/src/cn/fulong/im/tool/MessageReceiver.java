package cn.fulong.im.tool;

import javax.jms.Message;

public interface MessageReceiver {

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public abstract void onMessage(Message m);
	
	public String getReceiver();

	public void setReceiver(String receiver);

	public String getRecmsg();

	public void setRecmsg(String recmsg);

}