package cn.fulong.im.amqtool;

import cn.fulong.im.model.MessageModel;

public interface ReceiveMsg {
	public MessageModel recMsg(String receiver);
}