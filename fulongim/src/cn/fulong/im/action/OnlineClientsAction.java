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
			getClientList().addClient(client);
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
			getClientList().delClient(client);
			setIsonline("offline");
		}
		return "success";
	}
	
	//�ÿ��Ƿ�����
	public String isOnline() throws Exception {
		// TODO Auto-generated method stub
		if (!client.equals("") && getClientList().isClientOnline(client)) {
			setIsonline("online");
		}
		return "success";
	}
	
	//��ȡ�ÿ��б�
	private OnlineClients getClientList(){
		ApplicationContext clientcontext = MyContextFactory.getContext();
		OnlineClients clientslist=(OnlineClients) clientcontext.getBean("clientsList");
		return clientslist;
	}
}
