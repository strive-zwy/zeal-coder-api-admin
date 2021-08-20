package com.zealcoder.handler;

import com.zealcoder.enums.CommonEnum;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/20 16:59
 * @Description : 处理全局异常
 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

	//处理自定义的业务异常
	@ExceptionHandler(value = BizException.class)
	@ResponseBody
	public ResultBody bizExceptionHandler(HttpServletRequest req, BizException e){
		log.error("发生业务异常！原因是：{}",e.getErrorMsg());
		return ResultBody.error(e.getErrorCode(),e.getErrorMsg());
	}

	//处理空指针的异常
	@ExceptionHandler(value =NullPointerException.class)
	@ResponseBody
	public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e){
		log.error("发生空指针异常！原因是:",e);
		return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
	}


	@ExceptionHandler(value = Exception.class)
	public String exceptionHandler(Exception e) {
		System.out.println("未知异常！原因是:" + e);
		return e.getMessage();
	}
}
