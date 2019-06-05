package com.sgcc.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.sgcc.exception.ConsumerException;

/**
 * 
 * 
 * <p>
 * Title: ConsumerManager
 * </p>
 * 
 * <p>
 * Description: ConsumerBean管理器，从数据库中读取配置并实例化ConsumerBean，一个或多个
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class ConsumerManager implements ApplicationContextAware, InitializingBean,Manager {

	private ConsumerBean consumerBean;

	private static Map<String, ConsumerBean> consumerBeans = new HashMap<>();

	private ApplicationContext applicationContext;

	private JdbcTemplate jdbcTemplate;

	private final static String CID_DEFAULT = "CID_DEFAULT";

	private static Logger logger = LoggerFactory.getLogger(ConsumerManager.class);

	public void setConsumerBean(ConsumerBean consumerBean) {
		this.consumerBean = consumerBean;
	}

	@Override
	public void start() {
		if (consumerBeans.size() > 0) {
			logger.info("开启所有监听，consumerBean个数：" + consumerBeans.size());
			consumerBeans.forEach((consumerId, consumer) -> {
				if (!consumer.getSubscriptionTable().isEmpty()) {
					start(consumerId);
				}
			});
		}
	}
	@Override
	public void shutdown() {
		if (!consumerBeans.isEmpty()) {
			logger.info("关闭所有监听");
			consumerBeans.forEach((consumerId, consumer) -> {
				shutdown(consumerId);
			});
		}
	}

	/**
	 * 启动某个消费者
	 * 
	 * @param consumerId
	 */
	@Override
	public void start(String consumerId) {
		ConsumerBean consumer = consumerBeans.get(consumerId);
		logger.info("开启consumer,consumerId=" + consumerId);
		if (consumer != null) {
			consumer.start();
		} else {
			logger.warn("consumer不存在，开启失败！,consumerId=" + consumerId);
		}
	}

	/**
	 * 关闭某个消费者
	 * 
	 * @param consumerId
	 */
	@Override
	public void shutdown(String consumerId) {
		ConsumerBean consumer = consumerBeans.get(consumerId);
		logger.info("关闭consumer,consumerId=" + consumerId);
		if (consumer != null) {
			consumer.shutdown();
		} else {
			logger.warn("consumer不存在，关闭失败！,consumerId=" + consumerId);
		}
	}

	/**
	 * 重启某个消费者
	 * 
	 * @param consumerId
	 */
	@Override
	public void restart(String consumerId) {
		logger.info("重启消费者，consumerId"+consumerId);
		shutdown(consumerId);
		start(consumerId);
		logger.info("重启完成");
	}
	
	/**
	 * 启用某个consumer
	 */
	@Override
	public void enabled(String consumerId){
		logger.info("启用consumer,consumerId="+consumerId);
		ConsumerBean consumer=consumerBeans.get(consumerId);
		if(consumer==null){
			createConsumerBean(consumerId);
			consumer=consumerBeans.get(consumerId);
			consumer.start();
		}else{
			if(consumer.isClosed()){
				consumer.start();
			}
		}
		jdbcTemplate.update("update rkmq_consumer_config set enabled='0' where consumer_id=?", consumerId);
		jdbcTemplate.update("update rkmq_listener set enabled='0' where consumer_id=?", consumerId);
		logger.info("启用成功");
	}
	
	/**
	 * 禁用某个consumer
	 */
	@Override
	public void disabled(String consumerId){
		logger.info("禁用consumer,consumerId="+consumerId);
		ConsumerBean consumer=consumerBeans.get(consumerId);
		if(consumer!=null){
			consumer.shutdown();
			consumerBeans.remove(consumerId);
			jdbcTemplate.update("update rkmq_consumer_config set enabled='1' where consumer_id=?", consumerId);
			jdbcTemplate.update("update rkmq_listener set enabled='1' where consumer_id=?", consumerId);
			logger.info("禁用成功");
		}else{
			logger.warn("禁用失败，consumer不存在，consumerId="+consumerId);
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("开始初始化Consumer属性");
		/**
		 * 如果传了consumerBean，就所有的listener都用同一个配置，否则从数据库读取配置
		 */
		if (this.consumerBean == null) {
			logger.info("consumerBean为空，开始从数据库查找配置实例化consumerBean并设置属性");
			// TODO 从数据库中读取ConsumerBean的配置，动态的配置多个ConsumerBean
			createConsumerBean();
		} else {
			createSubscriptionTable(consumerBean, CID_DEFAULT);
		}
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 创建单个consumer
	 * @param consumerId
	 */
	private void createConsumerBean(String consumerId) {
		String sql = "select * from rkmq_consumer_config where enabled='0' and consumer_id=?";
		List<Map<String, Object>> consumerConfigs = jdbcTemplate.queryForList(sql, consumerId);
		logger.info("创建consumer，consumerId=" + consumerId);
		if (consumerConfigs.size() > 0) {
			consumerConfigs.stream().forEach(consumerConfig -> {
				ConsumerBean consumerBean = new ConsumerBean();
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.ConsumerId, consumerConfig.get("consumer_id").toString());
				properties.put(PropertyKeyConst.AccessKey, consumerConfig.get("access_key").toString());
				properties.put(PropertyKeyConst.SecretKey, consumerConfig.get("secret_key").toString());
				properties.put(PropertyKeyConst.ONSAddr, consumerConfig.get("ons_addr").toString());
				logger.info("连接属性：" + properties);
				consumerBean.setProperties(properties);
				this.createSubscriptionTable(consumerBean, consumerConfig.get("consumer_id").toString());
			});
		}else{
			throw new ConsumerException("Consumer创建失败,找不到相关配置,consumerId="+consumerId);
		}
	}
	
	/**
	 * 创建所有consumer
	 */
	private void createConsumerBean() {
		String sql = "select * from rkmq_consumer_config where enabled='0'";
		List<Map<String, Object>> consumerConfigs = jdbcTemplate.queryForList(sql);
		if (consumerConfigs.size() > 0) {
			consumerConfigs.stream().forEach(consumerConfig -> {
				ConsumerBean consumerBean = new ConsumerBean();
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.ConsumerId, consumerConfig.get("consumer_id").toString());
				properties.put(PropertyKeyConst.AccessKey, consumerConfig.get("access_key").toString());
				properties.put(PropertyKeyConst.SecretKey, consumerConfig.get("secret_key").toString());
				properties.put(PropertyKeyConst.ONSAddr, consumerConfig.get("ons_addr").toString());
				logger.info("连接属性：" + properties);
				consumerBean.setProperties(properties);
				this.createSubscriptionTable(consumerBean, consumerConfig.get("consumer_id").toString());
			});
		}else{
			logger.warn("Consumer创建失败,找不到相关配置");
		}
	}
	/**
	 * 创建监听类对象
	 * 
	 * @param consumerBean
	 * @param consumerId
	 */
	private void createSubscriptionTable(ConsumerBean consumerBean, String consumerId) {
		// 用反射生成消息监听对象，配置在数据库中
		StringBuilder sql = new StringBuilder("select * from rkmq_listener where enabled='0' ");
		List<Map<String, Object>> consumers = new ArrayList<>();
		if (consumerId == null || CID_DEFAULT.equals(consumerId)) {
			consumers = jdbcTemplate.queryForList(sql.toString());
		} else {
			sql.append(" and consumer_id=?");
			consumers = jdbcTemplate.queryForList(sql.toString(), consumerId);
		}
		Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
		if (consumers != null && !consumers.isEmpty()) {
			consumers.stream().forEach(consumer -> {
				Subscription subscription = new Subscription();
				subscription.setTopic(consumer.get("topic").toString());
				subscription.setExpression(consumer.get("tag").toString());
				try {
					Class<?> classz = Class.forName(consumer.get("listener_class_name").toString());
					logger.info("开始为" + classz.getName() + "创建对象");
					MessageListener messageListener = (MessageListener) classz.newInstance();
					/**
					 * 增强bean，让容器之外的bean获得自动注入的能力，这样反射生成的对象也可以用 @Autowire
					 * 注解了,spring牛逼
					 * 
					 */
					applicationContext.getAutowireCapableBeanFactory().autowireBean(messageListener);
					subscriptionTable.put(subscription, messageListener);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				}

			});
			consumerBean.setSubscriptionTable(subscriptionTable);
			consumerBeans.put(consumerId, consumerBean);
			logger.info("consumer初始化完成");
		} else {
			logger.warn("未找到相关Consumer！请检查数据库配置");
		}
	}

	@Override
	public boolean isStart(String consumerId) {
		ConsumerBean consumer=consumerBeans.get(consumerId);
		if(consumer!=null){
			return consumer.isStarted();
		}
		return false;
	}
}
