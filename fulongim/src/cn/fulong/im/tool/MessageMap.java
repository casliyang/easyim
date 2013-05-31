package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

public class MessageMap {
private static ConcurrentHashMap<String,String> messageList = null;

public static ConcurrentHashMap<String,String> getMessageMap() {
	if (messageList == null) {
		messageList = new ConcurrentHashMap<String,String>();
	}
	return messageList;
}
}
