package cn.fulong.im.tool;
	
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 用于保存所有"在线访客"
 * "在线访客"结构为：[onlineclient1,onlineclient2,onlineclient3...]
 * 注意“在线访客”与“活跃访客”不同，“活跃访客”指的是发起沟通的访客
 * “活跃访客”结构为：{[receiver1，sender1|sender2|sender3|...],[receiver2，sender1|sender2|sender3|...]....}
 * @author liyang
 *
 */
public class OnlineClients {
	
	private static CopyOnWriteArrayList<String> clients = null;
	public static CopyOnWriteArrayList<String> getOnlineClients() {
		if (clients == null) {
			clients = new CopyOnWriteArrayList<String>();
		}
		return clients;
	}
}
