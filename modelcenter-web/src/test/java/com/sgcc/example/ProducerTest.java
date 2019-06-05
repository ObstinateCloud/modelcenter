package com.sgcc.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.sgcc.JUnit4ClassRunner;
import com.sgcc.enu.MQMessage;
import com.sgcc.mq.ProducerManager;

/**
 * 测试发送mq消息
 */
/*
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-beans.xml" })
public class Demo {
	
	@Autowired
	private ProducerManager producerManager;
	
	@Test
	public void sendMessage(){
		ProducerBean producerBean=producerManager.getProducer("PID_TEST");
		Message message=new Message();
		message.setBody("hello world".getBytes());
		message.setTopic(MQMessage.TEST.topic());
		message.setTag(MQMessage.TEST.tag());
		producerBean.send(message);
	}
	
	public void print(){
		System.err.println("");
	}
}
*/
