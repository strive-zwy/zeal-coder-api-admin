package com.zealcoder.handler;

import com.zealcoder.enums.CommonEnum;
import com.zealcoder.exception.BizException;
import com.zealcoder.exception.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : zwy
 * @version : 1.0
 * @createTime : 2021/8/20 16:59
 * @Description : 处理全局异常
 */
@RestControllerAdvice
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
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseBody
	public ResultBody exceptionHandler(HttpServletRequest req, NullPointerException e){
		log.error("发生空指针异常！原因是:",e);
		return ResultBody.error(CommonEnum.BODY_NOT_MATCH);
	}


	//处理用户验证的异常
/*	@ExceptionHandler(value = UnknownAccountException.class)
	@ResponseBody
	public ResultBody UnknownAccountExceptionHandler(HttpServletRequest req, UnknownAccountException e){
		log.error("发生用户验证异常！原因是:",e);
		return ResultBody.error("-1","无效token1111");
	}*/

	//处理用户验证的异常
/*	@ExceptionHandler(value = UnauthenticatedException.class)
	@ResponseBody
	public ResultBody exceptionHandler(HttpServletRequest req, UnauthenticatedException e){
		log.error("发生用户验证异常！原因是:",e);
		return ResultBody.error("-1","无效token");
	}*/

	@ExceptionHandler(value = Exception.class)
	public void exceptionHandler(Exception e) {
		log.error("未知异常！原因是:\t" + e);
//		return e.getMessage();
	}
}
