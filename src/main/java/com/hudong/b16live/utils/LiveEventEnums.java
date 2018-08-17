package com.hudong.b16live.utils;

//腾讯云事件消息通知，通知类型
public enum LiveEventEnums {
	CUTOFFFLOW(0,"断流"),//断流
	PUSHFLOW(1,"推流"),//推流
	NEWVEDIO(100,"新的录制文件已生成"),//新的录制文件已生成
	NEWIMAGE(200,"新的截图文件已生成");//新的截图文件已生成
	private int eventType;
	private String desc;
	private LiveEventEnums(int eventType,String desc) {
		this.eventType = eventType;
		this.desc = desc;
	}
	public int getEventType() {
		return eventType;
	}
	public String getDesc() {
		return desc;
	}
}
