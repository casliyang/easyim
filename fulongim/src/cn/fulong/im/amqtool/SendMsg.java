package cn.fulong.im.amqtool;

import cn.fulong.im.model.MessageModel;


public interface SendMsg {
	public void msgSend(MessageModel mm);
}