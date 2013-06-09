package cn.fulong.im.tool;
	
import java.util.concurrent.ConcurrentHashMap;
/**
 * ���ڱ�������"���߿ͷ�"
 * "���߿ͷ�"�ṹΪ��{[servant1,informations],[servant2,informations]....}
 * informationsָ���ǿͷ������Ϣ�������Ժ������Ҫ�ķ�����Ϣ�ȣ�ĿǰΪ��
 *
 */
public class OnlineServantsMap {
	
	private static ConcurrentHashMap<String,String> servants = null;
	public static ConcurrentHashMap<String,String> getOnlineServants() {
		if (servants == null) {
			servants = new ConcurrentHashMap<String,String>();
		}
		return servants;
	}
}
