package com.imooc.miaosha.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.binding.BindingException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
   @ExceptionHandler(value=Exception.class)
   public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
	   e.printStackTrace();
	   if(e instanceof GlobalException){
		   GlobalException ex = (GlobalException)e;
		   CodeMsg cm = ex.getCm();
		   return Result.error(cm);
	   }else if(e instanceof BindException){
		   BindException ex = (BindException)e;
		   ObjectError error = ex.getAllErrors().get(0);
		   String msg=error.getDefaultMessage();
		    return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
	   }else{
		   return Result.error(CodeMsg.SERVER_ERROR);
	   }
   }
}
