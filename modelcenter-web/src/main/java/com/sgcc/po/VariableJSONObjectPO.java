package com.sgcc.po;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * 可用此类修改json的key，用于少量数据的修改

* <p>Title: VariableJSONObject</p>  

* <p>Description: </p>  

* @author mengjinyuan  

* @date 2019年3月14日
 */
public class VariableJSONObjectPO{

	//用来存放别名
	private Map<String, String> aliasMap=new HashMap<>();
	
	//是否驼峰转下划线
	private boolean underline=false;
	
	//json数据源
	private JSONObject jsonObject;
	
	public VariableJSONObjectPO(String json) {
		this.jsonObject=JSONObject.parseObject(json);
	}
	
	public VariableJSONObjectPO(JSONObject jsonObject) {
		this.jsonObject=jsonObject;
	}
	
	public VariableJSONObjectPO addAlias(String key,String alias){
		aliasMap.put(key, alias);
		return this;
	}

	/**
	 * 获取json对象，如果给某key设置了别名，则修改该key，并返回新对象
	 * @return
	 */
	public JSONObject getJsonObject() {
		JSONObject newJsonObject=new JSONObject();
		if(aliasMap.isEmpty()&&!underline){
			return this.jsonObject;
		}
		Set<String> keySet = jsonObject.keySet();
		String keyStr="";
		for(String key:keySet){
			if(aliasMap.get(key)==null){
				keyStr=key;
			}else{
				keyStr=aliasMap.get(key);
			}
			if(underline){
				keyStr=camelToUnderline(keyStr);
			}
			newJsonObject.put(keyStr, jsonObject.get(key));
		}
		return newJsonObject;
	}
	
	/**
	 * 驼峰转下划线
	 * @param param
	 * @return
	 */
	private String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
	
	public void setUnderline(boolean underline) {
		this.underline = underline;
	}
}
