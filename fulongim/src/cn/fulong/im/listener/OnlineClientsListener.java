package cn.fulong.im.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;

import cn.fulong.im.tool.MyContextFactory;
import cn.fulong.im.tool.OnlineClients;

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
        // TODO Auto-generated method stub
    	if (evt.getSession().getAttribute("clientID") != null) {
    		String client = (String)evt.getSession().getAttribute("clientID");
    		if (!client.equals("")) {
    			ApplicationContext clientcontext = MyContextFactory.getContext();
    			OnlineClients clientslist=(OnlineClients) clientcontext.getBean("clientsList");
    			clientslist.delClient(client);
			}
		}
    	
    	
    }

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
