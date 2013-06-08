package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

public class MessageMap {
	/**
	 *中转区是一个ConcurrentHashMap<String,String>，key是send&receiver，
	 *value是收到多条消息的拼接：
	 *[send&receiver,msg1&msg2&msg3]
	 *[send&receiver,msg1&msg2&msg3]
	 *……
	 */
	private static ConcurrentHashMap<String,String> messageList = null;
	public static ConcurrentHashMap<String,String> getMessageMap() {
		if (messageList == null) {
			messageList = new ConcurrentHashMap<String,String>();
		}
		return messageList;
	}
}
