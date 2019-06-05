package com.sgcc.mq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.sgcc.exception.ProducerException;

/**
 * 
 * 
 * <p>
 * Title: ProducerManager
 * </p>
 * 
 * <p>
 * Description:生产者管理
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class ProducerManager implements InitializingBean, Manager {

	private static Map<String, ProducerBean> producerBeans = new HashMap<>();

	protected JdbcTemplate jdbcTemplate;

	private static Logger logger = LoggerFactory.getLogger(ProducerManager.class);

	@Override
	public void start() {
		if (producerBeans.size() > 0) {
			logger.info("开启所有生产者，producerBean个数：" + producerBeans.size());
			producerBeans.forEach((producerId, producer) -> {
				start(producerId);
			});
		}
	}

	@Override
	public void shutdown() {
		if (!producerBeans.isEmpty()) {
			logger.info("关闭所有生产者");
			producerBeans.forEach((producerId, producer) -> {
				shutdown(producerId);
			});
		}
	}

	@Override
	public void start(String producerId) {
		ProducerBean producer = producerBeans.get(producerId);
		logger.info("开启producer,producerId=" + producerId);
		if (producer != null) {
			producer.start();
		} else {
			logger.warn("producer不存在，开启失败！,producerId=" + producerId);
		}
	}

	@Override
	public void shutdown(String producerId) {
		ProducerBean producer = producerBeans.get(producerId);
		logger.info("关闭producer,producerId=" + producerId);
		if (producer != null) {
			producer.shutdown();
		} else {
			logger.warn("producer不存在，关闭失败！,producerId=" + producerId);
		}

	}

	@Override
	public void restart(String producerId) {
		logger.info("重启消费者，producerId" + producerId);
		shutdown(producerId);
		start(producerId);
		logger.info("重启完成");
	}

	@Override
	public void enabled(String producerId) {
		logger.info("启用producer,producerId=" + producerId);
		ProducerBean producer = producerBeans.get(producerId);
		if (producer == null) {
			createProducer(producerId);
			producer = producerBeans.get(producerId);
			producer.start();
		} else {
			if (producer.isClosed()) {
				producer.start();
			}
		}
		jdbcTemplate.update("update rkmq_producer_config set enabled='0' where producer_id=?", producerId);
		logger.info("启用成功");
	}

	@Override
	public void disabled(String producerId) {
		logger.info("禁用producer,producerId=" + producerId);
		ProducerBean producer = producerBeans.get(producerId);
		if (producer != null) {
			producer.shutdown();
			producerBeans.remove(producerId);
			jdbcTemplate.update("update rkmq_producer_config set enabled='1' where producer_id=?", producerId);
			logger.info("禁用成功");
		} else {
			logger.warn("禁用失败，producer不存在，producerId=" + producerId);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("开始创建生产者");
		if (producerBeans.isEmpty()) {
			createProducer();
		}
		logger.info("生产者创建完成");
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public ProducerBean getProducer(String producerId) {
		return producerBeans.get(producerId);
	}

	public void setProducerBeans(Map<String, ProducerBean> producerBeans) {
		ProducerManager.producerBeans = producerBeans;
	}

	/**
	 * 创建所有生产者bean
	 */
	private void createProducer() {
		String sql = "select * from rkmq_producer_config where enabled='0'";
		List<Map<String, Object>> producerConfigs = jdbcTemplate.queryForList(sql);
		if (producerConfigs.size() > 0) {
			producerConfigs.stream().forEach(producerConfig -> {
				ProducerBean producerBean = new ProducerBean();
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.ProducerId, producerConfig.get("producer_id").toString());
				properties.put(PropertyKeyConst.AccessKey, producerConfig.get("access_key").toString());
				properties.put(PropertyKeyConst.SecretKey, producerConfig.get("secret_key").toString());
				properties.put(PropertyKeyConst.ONSAddr, producerConfig.get("ons_addr").toString());
				logger.info("连接属性：" + properties);
				producerBean.setProperties(properties);
				producerBeans.put(producerConfig.get("producer_id").toString(), producerBean);
			});
		} else {
			logger.warn("producer创建失败,找不到相关配置");
		}

	}

	/**
	 * 创建一个生产者bean
	 */
	private void createProducer(String producerId) {
		String sql = "select * from rkmq_producer_config where enabled='0' and producer_id=?";
		List<Map<String, Object>> producerConfigs = jdbcTemplate.queryForList(sql, producerId);
		if (producerConfigs.size() > 0) {
			producerConfigs.stream().forEach(producerConfig -> {
				ProducerBean producerBean = new ProducerBean();
				Properties properties = new Properties();
				properties.put(PropertyKeyConst.ProducerId, producerConfig.get("producer_id").toString());
				properties.put(PropertyKeyConst.AccessKey, producerConfig.get("access_key").toString());
				properties.put(PropertyKeyConst.SecretKey, producerConfig.get("secret_key").toString());
				properties.put(PropertyKeyConst.ONSAddr, producerConfig.get("ons_addr").toString());
				logger.info("连接属性：" + properties);
				producerBean.setProperties(properties);
				producerBeans.put(producerConfig.get("producer_id").toString(), producerBean);
			});
		} else {
			throw new ProducerException("producer创建失败,找不到相关配置,producerId="+producerId);
		}

	}

	@Override
	public boolean isStart(String producerId) {
		ProducerBean producer = producerBeans.get(producerId);
		if (producer != null) {
			return producer.isStarted();
		}
		return false;
	}
}
