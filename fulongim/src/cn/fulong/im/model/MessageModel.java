package cn.fulong.im.model;


public class MessageModel {
private String conversationID = "";
private String sender = "";
private String receiver = "";
private String sendTime = "";   //yyyy-mm-dd hh:mm
private String content = "";
public String getSendTime() {
	return sendTime;
}
public void setSendTime(String sendTime) {
	this.sendTime = sendTime;
}
public String getConversationID() {
	return conversationID;
}
public void setConversationID(String conversationID) {
	this.conversationID = conversationID;
}
public String getSender() {
	return sender;
}
public void setSender(String sender) {
	this.sender = sender;
}
public String getReceiver() {
	return receiver;
}
public void setReceiver(String receiver) {
	this.receiver = receiver;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}



}
