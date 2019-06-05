package com.sgcc.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 定时任务bean工厂类 把Job交给Spring来管理，这样Job就能使用由Spring产生的Bean了
 * <p>
 * Title: JobFactory
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class JobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
		Object jobInstance = super.createJobInstance(bundle);
		applicationContext.getAutowireCapableBeanFactory().autowireBean(jobInstance);
		return jobInstance;
	}
}