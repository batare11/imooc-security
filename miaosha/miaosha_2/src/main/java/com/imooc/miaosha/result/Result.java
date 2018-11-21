package com.imooc.miaosha.result;
/**
 * {
 *   "code": ,
 *   "msg": ,
 *   "data":{}/[]
 * }
 * @author 35-pxiaodong
 *
 */
public class Result<T> {
	
   private int code;
   private String msg;
   private T data;
   
   private Result(T data){
	   this.code=0;
	   this.msg="success";
	   this.data=data;
   }
   
   private Result(CodeMsg codemsg) {
	if (codemsg==null) {
		return;
	}
	this.code=codemsg.getCode();
	this.msg=codemsg.getMsg();
}

/**
    * 成功的时候调用
    * @return
    */
   public static <T> Result<T> success(T data){
	   return new Result<T>(data);
   }
   /**
    * 失败的时候调用
    * @return
    */
   public static <T> Result<T> error(CodeMsg codemsg){
	   return new Result<T>(codemsg);
   }

public int getCode() {
	return code;
}

public String getMsg() {
	return msg;
}

public T getData() {
	return data;
}

}
