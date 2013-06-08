package cn.fulong.im.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;

import cn.fulong.im.tool.MyContextFactory;
import cn.fulong.im.tool.OnlineClients;

/**
 * Application Lifecycle Listener implementation class OnlineClientsListener
 * 功能：当访客非正常
 */
@WebListener
public class OnlineClientsListener implements HttpSessionListener {
	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     * 当session过期时，OnlineClients中删除当前session相关的访客
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
