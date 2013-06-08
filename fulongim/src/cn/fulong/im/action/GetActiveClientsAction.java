package cn.fulong.im.action;

import java.util.concurrent.ConcurrentHashMap;

import cn.fulong.im.tool.ActiveClientsMap;

import com.opensymphony.xwork2.ActionSupport;

public class GetActiveClientsAction  extends ActionSupport  {

	private static final long serialVersionUID = 1L;
	String activeClients = "";
	String receiver = "";
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getActiveClients() {
		return activeClients;
	}
	public void setActiveClients(String activeClients) {
		this.activeClients = activeClients;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		ConcurrentHashMap<String,String> activeClientsMap = ActiveClientsMap.getActiveClientsMap();
		while (true) {
			if (activeClientsMap.containsKey(receiver) && !activeClientsMap.get(receiver).equals("")) {
				this.setActiveClients(activeClientsMap.get(receiver));
				activeClientsMap.remove(receiver);
				break;
				}
			Thread.sleep(1000);
		}

		return super.execute();
	}


}
