package cn.fulong.im.tool;
	
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * 用于保存所有在线的访客
 * 注意“在线访客”与“活跃访客”不同，“活跃访客”指的是发起沟通的访客
 * @author liyang
 *
 */
public class OnlineClients {
	
	private static CopyOnWriteArrayList<String> clients = new CopyOnWriteArrayList<String>();
	
	public void addClient(String clientName){
		if (!clientName.equals("") && !clients.contains(clientName)) {
			clients.add(clientName);
		}
	}
	
	public void delClient(String clientName){
		if (!clientName.equals("") && clients.contains(clientName)) {
			clients.remove(clientName);
		}
	}
	
	public boolean isClientOnline(String clientName){
		if (!clientName.equals("")) {
			return clients.contains(clientName);
		}
		return false;
	}
}
