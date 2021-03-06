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
	
	//新增访客
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!client.equals("")) {
			getClientList().addClient(client);
			setIsonline("online");
		}
		return "success";
	}
	
	//删除访客
	public String del() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("")) {
			getClientList().delClient(client);
			setIsonline("offline");
		}
		return "success";
	}
	
	//访客是否在线
	public String isOnline() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("") && getClientList().isClientOnline(client)) {
			setIsonline("online");
		}
		return "success";
	}
	
	//获取访客列表
	private ClientsList getClientList(){
		ApplicationContext clientcontext = MyContextFactory.getContext();
		ClientsList clientslist=(ClientsList) clientcontext.getBean("clientsList");
		return clientslist;
	}
}
