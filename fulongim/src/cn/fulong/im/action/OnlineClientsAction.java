package cn.fulong.im.action;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import cn.fulong.im.tool.OnlineClients;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * @version 1.0
 * @author liyang
 * ͨ�������� OnlineClients���������������¹��ܣ�
 * 1.��ӷÿ�
 * 2.ɾ���ÿͣ����û���ʽ�رնԻ���ʱʹ�ã�����sessionlistener����session�����٣�
 * 3.��ȡ�ÿ�����״̬
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
	 * ����Ajax������OnlineClients������߷ÿͣ��������߷ÿ�id���õ�session��
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!client.equals("")) {
			//��OnlineClients������߷ÿ�
			CopyOnWriteArrayList<String> onlineClients = OnlineClients.getOnlineClients();
			//�����ظ����
			if (!onlineClients.contains(client)) {
				onlineClients.add(client);
			}
			
			setIsonline("online");
			
			//�����߷ÿ�id���õ�session��
			ActionContext actionContext = ActionContext.getContext();
	        Map<String,Object> session = actionContext.getSession();
	        session.put("clientID",client);
		}
		return "success";
	}
	
	/**
	 * ���û���ʽ�رնԻ���ʱʹ�á�
	 * ����HttpSessionListener����session������
	 * HttpSessionListenerһ��������session���٣��͵�OnlineClientsȥɾ���ÿ�
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("")) {
			CopyOnWriteArrayList<String> onlineClients = OnlineClients.getOnlineClients();
			onlineClients.remove(client);
			setIsonline("offline");
		}
		return "success";
	}
	
	//�ÿ��Ƿ�����
	public String isOnline() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("")) {
			CopyOnWriteArrayList<String> onlineClients = OnlineClients.getOnlineClients();
			if (onlineClients.contains(client)) {
				setIsonline("online");
			}else setIsonline("offline");
			
		}
		return "success";
	}
	
	//�ÿ��Ƿ�������
	public String isOffline() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("")) {
			CopyOnWriteArrayList<String> onlineClients = OnlineClients.getOnlineClients();
			while (true) {
				if (!onlineClients.contains(client)) {
					setIsonline("offline");
					break;
				}
				Thread.sleep(5000);
			}
		}
		return "success";
	}
	
}
