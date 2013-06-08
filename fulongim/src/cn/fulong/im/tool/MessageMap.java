package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

public class MessageMap {
	/**
	 *��ת����һ��ConcurrentHashMap<String,String>��key��send&receiver��
	 *value���յ�������Ϣ��ƴ�ӣ�
	 *[send&receiver,msg1&msg2&msg3]
	 *[send&receiver,msg1&msg2&msg3]
	 *����
	 */
	private static ConcurrentHashMap<String,String> messageList = null;
	public static ConcurrentHashMap<String,String> getMessageMap() {
		if (messageList == null) {
			messageList = new ConcurrentHashMap<String,String>();
		}
		return messageList;
	}
}
