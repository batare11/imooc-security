package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix{
    private int expireSeconds;
    private String prefix;
    
    public BasePrefix(String prefix){//0代表永不过期
    	this(0, prefix);
    }
    
    public BasePrefix(int expireSeconds,String prefix){
    	this.prefix=prefix;
    	this.expireSeconds=expireSeconds;
    }
    
	@Override
	public int expireSeconds() {//0默认代表永不过期
		return expireSeconds;
	}

	@Override
	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":"+prefix;
	}

}
