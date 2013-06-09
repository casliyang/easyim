package cn.fulong.im.tool;
	
import java.util.concurrent.ConcurrentHashMap;
/**
 * 用于保存所有"在线访客"
 * "在线访客"结构为：[onlineclient1,onlineclient2,onlineclient3...]
 * 注意“在线访客”与“活跃访客”不同，“活跃访客”指的是发起沟通的访客
 * 
 * "在线访客"结构为：{[client1,informations],[client2,informations]....}
 * informations指的是页面监控到的访客相关信息，包括浏览器，操作系统，ip，所在地等
 * 
 * 相应的，"活跃访客"结构为：{[receiver1，sender1|sender2|sender3|...],[receiver2，sender1|sender2|sender3|...]....}
 * @author liyang
 *
 */
public class OnlineClientsMap {
	
	private static ConcurrentHashMap<String,String> clients = null;
	public static ConcurrentHashMap<String,String> getOnlineClients() {
		if (clients == null) {
			clients = new ConcurrentHashMap<String,String>();
		}
		return clients;
	}
}
