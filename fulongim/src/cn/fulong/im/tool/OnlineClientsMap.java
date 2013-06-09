package cn.fulong.im.tool;
	
import java.util.concurrent.ConcurrentHashMap;
/**
 * ���ڱ�������"���߷ÿ�"
 * "���߷ÿ�"�ṹΪ��[onlineclient1,onlineclient2,onlineclient3...]
 * ע�⡰���߷ÿ͡��롰��Ծ�ÿ͡���ͬ������Ծ�ÿ͡�ָ���Ƿ���ͨ�ķÿ�
 * 
 * "���߷ÿ�"�ṹΪ��{[client1,informations],[client2,informations]....}
 * informationsָ����ҳ���ص��ķÿ������Ϣ�����������������ϵͳ��ip�����ڵص�
 * 
 * ��Ӧ�ģ�"��Ծ�ÿ�"�ṹΪ��{[receiver1��sender1|sender2|sender3|...],[receiver2��sender1|sender2|sender3|...]....}
 * @author liyang
 *
 */
public class OnlineClientsMap {
	
	private static ConcurrentHashMap<String,String> clients = null;
	public static ConcurrentHashMap<String,String> getOnlineClients() {
		if (clients == null) {
			clients = new ConcurrentHashMap<String,String>();
		}
		return clients;
	}
}
