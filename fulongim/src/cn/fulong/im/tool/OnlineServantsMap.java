package cn.fulong.im.tool;
	
import java.util.concurrent.ConcurrentHashMap;
/**
 * 用于保存所有"在线客服"
 * "在线客服"结构为：{[servant1,informations],[servant2,informations]....}
 * informations指的是客服相关信息，包括以后可能需要的分组信息等，目前为空
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
