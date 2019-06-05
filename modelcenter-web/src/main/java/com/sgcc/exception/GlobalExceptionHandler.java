package com.sgcc.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.sgcc.web.dto.output.ResultDTO;

/**
 * 
 * 
 * <p>
 * Title: GlobalExceptionHandler
 * </p>
 * 
 * <p>
 * Description:全局异常处理器
 * </p>
 * 
 * @author mengjinyuan
 * 
 * @date 2019年3月14日
 */
public class GlobalExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = Logger.getLogger(this.getClass());

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		logger.error("============发生异常===========");
		logger.error("----异常拦截----");
		ResultDTO message = ResultDTO.error(exception.getMessage());
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().print(JSONObject.toJSONString(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.error("============================");
		return null;
	}
}