package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

public class ActiveClientsMap {
/**
 * ActiveClient 定义为向客服发送消息的访客
 * 结构为：【receiver，sender1|sender2|sender3|...】
 * 当消息监听器监听到消息，并向消息中转区中写消息时，会判断发消息的
 */
	
	private static ConcurrentHashMap<String,String> activeClients = null;
	public static ConcurrentHashMap<String,String> getActiveClientsMap() {
		if (activeClients == null) {
			activeClients = new ConcurrentHashMap<String,String>();
		}
		return activeClients;
	}
}
