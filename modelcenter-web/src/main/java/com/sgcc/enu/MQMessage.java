package com.sgcc.enu;

/**
 * 

* <p>Title: MQMessage</p>  

* <p>Description: 生产者发送mq消息的topic和tag枚举</p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public enum MQMessage {
	
	TEST("test","test_tag"),
	UPDATE("test","updateDate");
	
    private String topic;
    private String tag;

    MQMessage(String topic,String tag) {
    	this.topic=topic;
    	this.tag=tag;
    }
    
    public String topic(){
    	return this.topic;
    } 
    
    public String tag(){
    	return this.tag;
    }
}