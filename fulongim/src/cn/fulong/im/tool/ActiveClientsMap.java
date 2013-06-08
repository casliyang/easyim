package cn.fulong.im.tool;

import java.util.concurrent.ConcurrentHashMap;

public class ActiveClientsMap {
/**
 * ActiveClient ����Ϊ��ͷ�������Ϣ�ķÿ�
 * �ṹΪ����receiver��sender1|sender2|sender3|...��
 * ����Ϣ��������������Ϣ��������Ϣ��ת����д��Ϣʱ�����жϷ���Ϣ��
 */
	
	private static ConcurrentHashMap<String,String> activeClients = null;
	public static ConcurrentHashMap<String,String> getActiveClientsMap() {
		if (activeClients == null) {
			activeClients = new ConcurrentHashMap<String,String>();
		}
		return activeClients;
	}
}
