package com.sgcc.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

public class ConsumerTest {

	public static void main(String[] args) {
		ConsumerBean consumerBean = new ConsumerBean();
		Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_TEST100");
        properties.put(PropertyKeyConst.AccessKey, "a991a528e0aa41a19a5ba3921115b2f6");
        properties.put(PropertyKeyConst.SecretKey, "zsH4uH0I8MT6kfHGK2kQB4OWGG4=");
        properties.put(PropertyKeyConst.ONSAddr, "http://172.169.101.121:8080/rocketmq/nsaddr4broker-internal");
		consumerBean.setProperties(properties);

		Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();

		Subscription subscription = new Subscription();
		subscription.setTopic("test");
		subscription.setExpression("*");
		try {
			Class<?> classz = Class.forName("com.sgcc.mq.listener.TestListener");
			MessageListener messageListener = (MessageListener) classz.newInstance();
			subscriptionTable.put(subscription, messageListener);
		} catch (Exception e) {
			e.printStackTrace();
		}
		consumerBean.setSubscriptionTable(subscriptionTable);
		System.out.println("开启");
		consumerBean.start();
	}
}
