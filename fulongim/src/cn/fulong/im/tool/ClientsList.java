package cn.fulong.im.tool;
	
import java.util.concurrent.CopyOnWriteArrayList;
	
public class ClientsList {
	
	private static CopyOnWriteArrayList<String> clients = null;
	
	public CopyOnWriteArrayList<String> getClients() {
		if (clients == null) {
			clients = new CopyOnWriteArrayList<String>();
		}
		return clients;
	}
	
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
