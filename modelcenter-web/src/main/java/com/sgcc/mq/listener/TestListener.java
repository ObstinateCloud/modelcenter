package com.sgcc.mq.listener;


import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

public class TestListener implements MessageListener{
	
	@Override
	public Action consume(Message message, ConsumeContext context) {
		System.out.println("收到消息，message="+message+" context="+context);
		return Action.CommitMessage;
	}
}
