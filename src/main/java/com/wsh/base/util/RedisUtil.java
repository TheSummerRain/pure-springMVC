package com.wsh.base.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	// Redis服务器IP
	//private static String ADDR = "123.57.55.43";
	private static String ADDR = "101.200.196.164";

	// Redis的端口号
	private static Integer PORT = 9736;

	private static String AUTH = "149f31d8f51474e9298f1553";

	// 可用连接实例的最大数目，默认值为8；

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {

		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config, ADDR, PORT, MAX_WAIT, AUTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				resource.select(1);
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static void main(String[] args) {

		System.out.println(getJedis());
	}
}
