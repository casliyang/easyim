package cn.fulong.im.tool;

import java.util.concurrent.CopyOnWriteArrayList;

public class ClientsList {
	
	private final static CopyOnWriteArrayList<String> clients = new CopyOnWriteArrayList<String>();;

	public CopyOnWriteArrayList<String> getClients() {
		return clients;
	}

	public void addClient(String clientName){
		if (!clientName.equals("") && !clients.contains(clientName)) {
			clients.add(clientName);
		}
	}
	
	public void delClient(String clientName){
		if (!clientName.equals("")) {
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
