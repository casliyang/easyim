package cn.fulong.im.action;

import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.fulong.im.tool.OnlineServantsMap;

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
public class OnlineServantsAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	String servant = "";
	String isonline = "offline";
	String informations = "";
	String onlineServantsStr = "";
	

	public String getOnlineServantsStr() {
		return onlineServantsStr;
	}

	public void setOnlineServantsStr(String onlineServantsStr) {
		this.onlineServantsStr = onlineServantsStr;
	}

	public String getServant() {
		return servant;
	}

	public void setServant(String servant) {
		this.servant = servant;
	}
	
	public String getInformations() {
		return informations;
	}
	
	public void setInformations(String informations) {
		this.informations = informations;
	}

	public String getIsonline() {
		return isonline;
	}
	
	public void setIsonline(String isonline) {
		this.isonline = isonline;
	}
	

	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub		
		return "success";
	}
	
	/**
	 * 接收Ajax请求，向OnlineServantsMap添加在线客服，并将在线客服id设置到session中
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!servant.equals("")) {
			//向OnlineClients添加在线访客
			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
			//避免重复添加
			if (!onlineServants.contains(servant)) {
				onlineServants.put(servant, informations);
			}
			
			setIsonline("online");
			
			//将在线访客id设置到session中
			ActionContext actionContext = ActionContext.getContext();
	        Map<String,Object> session = actionContext.getSession();
	        session.put("servantID",servant);
		}
		return "success";
	}
	
	/**
	 * 当用户显式关闭对话框时使用。
	 * 否则靠HttpSessionListener监听session的销毁
	 * HttpSessionListener一旦监听到session销毁，就到OnlineServantsMap去删除访客
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
		// TODO Auto-generated method stub
		if (!servant.equals("")) {
			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
			onlineServants.remove(servant);
			setIsonline("offline");
		}
		return "success";
	}
	
	//客服是否在线
	public String isOnline() throws Exception {
		// TODO Auto-generated method stub
		if (!servant.equals("")) {
			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
			if (onlineServants.containsKey(servant)) {
				setIsonline("online");
			}else setIsonline("offline");
			
		}
		return "success";
	}
	
	//获取所有的在线客服id
	public String getAll() throws Exception {
		// TODO Auto-generated method stub
		ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
		Enumeration<String> servants = onlineServants.keys();
		while (servants.hasMoreElements()) {
			onlineServantsStr += servants.nextElement() + "|";
			
		}
		return "success";
	}
	
	//访客是否已下线
	//长连接，慎用
	public String isOffline() throws Exception {
		// TODO Auto-generated method stub
		if (!servant.equals("")) {
			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
			while (true) {
				if (!onlineServants.containsKey(servant)) {
					setIsonline("offline");
					break;
				}
				Thread.sleep(5000);
			}
		}
		return "success";
	}
	
}
