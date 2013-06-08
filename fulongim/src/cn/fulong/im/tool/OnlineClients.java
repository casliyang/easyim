package cn.fulong.im.tool;
	
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * ���ڱ����������ߵķÿ�
 * ע�⡰���߷ÿ͡��롰��Ծ�ÿ͡���ͬ������Ծ�ÿ͡�ָ���Ƿ���ͨ�ķÿ�
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
