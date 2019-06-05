package com.sgcc.webservice;

/**
 * 

* <p>Title: SynchronizedDataService</p>  

* <p>Description: 数据同步接口</p>  

* @author mengjinyuan  

* @date 2019年3月19日
 */
public interface SynchronizedDataService  {

	/**数据同步
	 * @param tableName表名
	 * @param data 数据
	 * @return
	 */
    public String synchronizedData(String tableName,String data);
}