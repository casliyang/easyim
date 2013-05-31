package cn.fulong.im.action;

import org.springframework.context.ApplicationContext;

import cn.fulong.im.tool.ClientsList;
import cn.fulong.im.tool.MyContextFactory;

import com.opensymphony.xwork2.ActionSupport;

public class ClientStatusAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	String client = "";
	String isonline = "offline";
	
	public String getIsonline() {
		return isonline;
	}

	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub		
		return "success";
	}
	
	//ÐÂÔö·Ã¿Í
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!client.equals("")) {
			getClientList().addClient(client);
			setIsonline("online");
		}
		return "success";
	}
	
	public String del() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("")) {
			getClientList().delClient(client);
			setIsonline("offline");
		}
		return "success";
	}
	
	public String isOnline() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("") && getClientList().isClientOnline(client)) {
			setIsonline("online");
		}
		return "success";
	}
	
	private ClientsList getClientList(){
		ApplicationContext clientcontext = MyContextFactory.getContext();
		ClientsList clientslist=(ClientsList) clientcontext.getBean("clientsList");
		return clientslist;
	}
}
