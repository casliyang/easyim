package cn.fulong.im.listener;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.fulong.im.tool.OnlineClientsMap;
import cn.fulong.im.tool.OnlineServantsMap;

/**
 * Application Lifecycle Listener implementation class OnlineClientsListener
 * ���ܣ����ÿͷ�����
 */
@WebListener
public class OnlineClientsListener implements HttpSessionListener {
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     * ��session����ʱ��OnlineClients��ɾ����ǰsession��صķÿ�
     */
    public void sessionDestroyed(HttpSessionEvent evt) {
        //���÷ÿ��˳�
    	if (evt.getSession().getAttribute("clientID") != null) {
    		String client = (String)evt.getSession().getAttribute("clientID");
    		if (!client.equals("")) {
    			ConcurrentHashMap<String,String> onlineClients = OnlineClientsMap.getOnlineClients();
    			onlineClients.remove(client);
			}
		}
		
		//���ÿͷ��˳�
    	if (evt.getSession().getAttribute("servantID") != null) {
    		String servant = (String)evt.getSession().getAttribute("servantID");
    		if (!servant.equals("")) {
    			ConcurrentHashMap<String,String> onlineServants = OnlineServantsMap.getOnlineServants();
    			onlineServants.remove(servant);
			}
		}
    	
    	
    }

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
