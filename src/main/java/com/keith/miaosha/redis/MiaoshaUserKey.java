package com.keith.miaosha.redis;

public class MiaoshaUserKey extends BasePrefix {

	//token过期时间
	public static final int TOKEN_EXPIRE = 3600*24 * 2;
	private MiaoshaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	//tk作为前缀来区别其他
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE,"tk");
	//永久保存用户id
	public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
}
