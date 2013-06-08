package cn.fulong.im.tool;
	
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * ���ڱ�������"���߷ÿ�"
 * "���߷ÿ�"�ṹΪ��[onlineclient1,onlineclient2,onlineclient3...]
 * ע�⡰���߷ÿ͡��롰��Ծ�ÿ͡���ͬ������Ծ�ÿ͡�ָ���Ƿ���ͨ�ķÿ�
 * ����Ծ�ÿ͡��ṹΪ��{[receiver1��sender1|sender2|sender3|...],[receiver2��sender1|sender2|sender3|...]....}
 * @author liyang
 *
 */
public class OnlineClients {
	
	private static CopyOnWriteArrayList<String> clients = null;
	public static CopyOnWriteArrayList<String> getOnlineClients() {
		if (clients == null) {
			clients = new CopyOnWriteArrayList<String>();
		}
		return clients;
	}
}
