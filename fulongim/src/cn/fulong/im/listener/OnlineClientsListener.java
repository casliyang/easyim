package cn.fulong.im.listener;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.fulong.im.tool.OnlineClientsMap;
import cn.fulong.im.tool.OnlineServantsMap;

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
        //设置访客退出
    	if (evt.getSession().getAttribute("clientID") != null) {
    		String client = (String)evt.getSession().getAttribute("clientID");
    		if (!client.equals("")) {
    			ConcurrentHashMap<String,String> onlineClients = OnlineClientsMap.getOnlineClients();
    			onlineClients.remove(client);
			}
		}
		
		//设置客服退出
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
