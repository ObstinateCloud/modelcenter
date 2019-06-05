package com.sgcc.mq.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.sgcc.web.entity.Demo;
import com.sgcc.web.entity.DemoExample;
import com.sgcc.web.entity.DemoExample.Criteria;
import com.sgcc.web.mapper.DemoMapper;

public class MyMessageListener implements MessageListener{
	
	private static Logger logger=LoggerFactory.getLogger(MyMessageListener.class);
	
	@Autowired
	private DemoMapper demoDao;
	
	@Override
	public Action consume(Message message, ConsumeContext context) {
		logger.info("收到消息，message="+message+" context="+context);
		
		DemoExample demoExample=new DemoExample();
		Criteria a=demoExample.createCriteria();
		a.andUsernameLike("%张");
		List<Demo> list=demoDao.selectByExample(demoExample);
		System.out.println("查询结果："+list);
		return Action.CommitMessage;
	}
}
