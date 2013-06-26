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
 * ͨ�������� OnlineClients���������������¹��ܣ�
 * 1.��ӷÿ�
 * 2.ɾ���ÿͣ����û���ʽ�رնԻ���ʱʹ�ã�����sessionlistener����session�����٣�
 * 3.��ȡ�ÿ�����״̬
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
	 * ����Ajax������OnlineServantsMap������߿ͷ����������߿ͷ�id���õ�session��
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// TODO Auto-generated method stub	
		if (!servant.equals("")) {
			//��OnlineClients������߷ÿ�
			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
			//�����ظ����
			if (!onlineServants.contains(servant)) {
				onlineServants.put(servant, informations);
			}
			
			setIsonline("online");
			
			//�����߷ÿ�id���õ�session��
			ActionContext actionContext = ActionContext.getContext();
	        Map<String,Object> session = actionContext.getSession();
	        session.put("servantID",servant);
		}
		return "success";
	}
	
	/**
	 * ���û���ʽ�رնԻ���ʱʹ�á�
	 * ����HttpSessionListener����session������
	 * HttpSessionListenerһ��������session���٣��͵�OnlineServantsMapȥɾ���ÿ�
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
	
	//�ͷ��Ƿ�����
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
	
	//��ȡ���е����߿ͷ�id
	public String getAll() throws Exception {
		// TODO Auto-generated method stub
		ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
		Enumeration<String> servants = onlineServants.keys();
		while (servants.hasMoreElements()) {
			onlineServantsStr += servants.nextElement() + "|";
			
		}
		return "success";
	}
	
	//�ÿ��Ƿ�������
	//�����ӣ�����
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
