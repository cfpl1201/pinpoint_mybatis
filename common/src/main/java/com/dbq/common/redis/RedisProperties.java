package com.dbq.common.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ConfigurationProperties(prefix = RedisProperties.DS, ignoreUnknownFields = false)
public class RedisProperties {

	/**
	 * 对应配置文件里的配置键
	 */
	public final static String DS="redis";

	@Value("${redis.host}")
	private String host;

	@Value("${redis.port}")
	private String port;

	/**
	 * 设置默认值为空,配置文件中为空时会报错
	 */
	@Value("${redis.auth:}")
	private String auth;

	@Value("${redis.useCluster}")
	private String useCluster;

	@Value("${redis.nodes}")
	private String nodes;

	/**
	 * 可用连接实例的最大数目，默认值为8；
	 * 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	 */
	public static final int MAX_ACTIVE = -1;

	/**
	 * 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	 */
	public static final int MAX_IDLE = 200;

	/**
	 * 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	 */
	public static final int MAX_WAIT = 10000;

	public static final int TIMEOUT = 10000;

	/**
	 * 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	 */
	public static final boolean TEST_ON_BORROW = true;
	private ExecutorService threadPool = Executors
			.newFixedThreadPool(16);

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public ExecutorService getThreadPool() {
		return threadPool;
	}
	public void setThreadPool(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}

	public String getUseCluster() {
		return useCluster;
	}

	public void setUseCluster(String useCluster) {
		this.useCluster = useCluster;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}
}