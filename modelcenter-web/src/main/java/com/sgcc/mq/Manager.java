package com.sgcc.mq;

import javax.sql.DataSource;

public interface Manager {
	
	/**
	 * 启动所有
	 */
	public void start();
	
	/**
	 * 关闭所有
	 */
	public void shutdown();
	
	/**
	 * 开启某个
	 * @param id
	 */
	public void start(String id);
	
	/**
	 * 关闭某个
	 * @param id
	 */
	public void shutdown(String id);
	
	/**
	 * 重启某个
	 * @param id
	 */
	public void restart(String id);
	
	/**
	 * 启用某个
	 * @param id
	 */
	public void enabled(String id);
	
	/**
	 * 禁用某个
	 * @param id
	 */
	public void disabled(String id);
	
	/**
	 * 是否开启
	 * @param id
	 */
	public boolean isStart(String id);
	
	/**
	 * 设置数据源
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource);
}
