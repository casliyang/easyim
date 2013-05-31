package cn.fulong.im.amqtool;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnection;
import org.apache.activemq.pool.PooledConnectionFactory;

public class MQPoolUtil {

    private static PooledConnection conn;
    
    public static void init() {
        String url = "failover:(tcp://localhost:61616)?initialReconnectDelay=1000&timeout=3000&startupMaxReconnectAttempts=2";
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(
        		"imuser",
                "fulong",
                url);
        try {
            PooledConnectionFactory poolFactory = new PooledConnectionFactory(factory);
            conn = (PooledConnection) poolFactory.createConnection();
            conn.start();
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public static void destroy(){
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    
    public static PooledConnection getConn() {
    	if (conn == null) {
			init();
		}
        return conn;
    }

    
    public static void setConn(PooledConnection conn) {
    
        MQPoolUtil.conn = conn;
    }
}