package cn.fulong.im.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import cn.fulong.im.tool.MyContextFactory;
import cn.fulong.im.tool.OnlineClients;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @version 1.0
 * @author liyang
 * 通过该类与 OnlineClients做交互，包括以下功能：
 * 1.添加访客
 * 2.删除访客（当用户显式关闭对话框时使用，否则靠sessionlistener监听session的销毁）
 * 3.获取访客在线状态
 */
public class OnlineClientsAction extends ActionSupport {
	
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
	
	/**
	 * 接收Ajax请求，向OnlineClients添加在线访客，并将在线访客id设置到session中
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!client.equals("")) {
			//向OnlineClients添加在线访客
			getClientList().addClient(client);
			setIsonline("online");
			
			//将在线访客id设置到session中
			ActionContext actionContext = ActionContext.getContext();
	        Map<String,Object> session = actionContext.getSession();
	        session.put("clientID",client);
		}
		return "success";
	}
	
	/**
	 * 当用户显式关闭对话框时使用。
	 * 否则靠HttpSessionListener监听session的销毁
	 * HttpSessionListener一旦监听到session销毁，就到OnlineClients去删除访客
	 * @return
	 * @throws Exception
	 */
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
	private OnlineClients getClientList(){
		ApplicationContext clientcontext = MyContextFactory.getContext();
		OnlineClients clientslist=(OnlineClients) clientcontext.getBean("clientsList");
		return clientslist;
	}
}
