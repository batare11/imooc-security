package com.imooc.miaosha.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {
	Logger log=Logger.getLogger(RedisService.class);
	@Autowired
	JedisPool jedisPool;
	/**
	 * 获取单位对象
	 * @param prfix
	 * @param key
	 * @param clazz
	 * @return
	 */
  public <T> T get(KeyPrefix prfix,String key,Class<T> clazz){
	  Jedis jedis = null;
	  try {
		log.info("是否获取到key本身的取值"+key);
		jedis = jedisPool.getResource();
		log.info("是否正确获取jedis对象"+jedis == null ?0:1);
		String realKey = prfix.getPrefix()+key;
		String str = jedis.get(realKey);
		log.info("是否正确获取到key的值"+str);
		T t = stringToBean(str,clazz);
		return t;
	} finally{
//		log.error("获取redis值出现错误--get方法");
//		log.error(e.getMessage());
		returnToPool(jedis);
	}
  }
  /**
   * 判断是否存在
   * @param prfix
   * @param key
   * @return
   */
  public <T> boolean exists(KeyPrefix prfix,String key){
	  Jedis jedis = null;
	  try {
		jedis = jedisPool.getResource();
		String realKey = prfix.getPrefix()+key;
		return jedis.exists(realKey);
	} finally{
		returnToPool(jedis);
	}
  }
  /**
   * 增加
   * @param prfix
   * @param key
   * @return
   */
  public <T> Long incr(KeyPrefix prfix,String key){
	  Jedis jedis = null;
	  try {
		jedis = jedisPool.getResource();
		String realKey = prfix.getPrefix()+key;
		return jedis.incr(realKey);
	} finally{
		returnToPool(jedis);
	}
  }
  /**
   * 减少
   * @param prfix
   * @param key
   * @return
   */
  public <T> Long decr(KeyPrefix prfix,String key){
	  Jedis jedis = null;
	  try {
		jedis = jedisPool.getResource();
		String realKey = prfix.getPrefix()+key;
		return jedis.decr(realKey);
	} finally{
		returnToPool(jedis);
	}
  }
  /**
   * 设置对象
   * @param prfix
   * @param key
   * @param value
   * @return
   */
  public <T> boolean set(KeyPrefix prfix,String key,T value){
	  Jedis jedis = null;
	  try {
		jedis = jedisPool.getResource();
		String str = beanToString(value);
		if(str == null || str.length()<=0){
			return false;
		}
		String realKey = prfix.getPrefix()+key;
		int seconds = prfix.expireSeconds();
		if(seconds <= 0){
			jedis.set(realKey, str);
		}else{
			jedis.setex(realKey,seconds,str);
		}
		return true;
	} finally{
		returnToPool(jedis);
	}
  }
  private <T> String beanToString(T value) {
	  if(value == null){
		  return null;
	  }
	  Class<?> clazz = value.getClass();
	  if(clazz==int.class || clazz==Integer.class){
		  return ""+value;
	  }else if(clazz==String.class){
		  return (String)value;
	  }else if(clazz==long.class || clazz==Long.class){
		  return ""+value;
	  }else{
		  return JSON.toJSONString(value);
	  }
}
private <T> T stringToBean(String str,Class<T> clazz) {
	if(str == null || str.length()<0){
      return null;		
	}
	  if(clazz==int.class || clazz==Integer.class){
		  return (T)Integer.valueOf(str);
	  }else if(clazz==String.class){
		  return (T)str;
	  }else if(clazz==long.class || clazz==Long.class){
		  return (T)Long.valueOf(str);
	  }else{
		  return JSON.toJavaObject(JSON.parseObject(str), clazz);
	  }
}
private void returnToPool(Jedis jedis) {
	if (jedis != null) {
		jedis.close();
	}
}
}
