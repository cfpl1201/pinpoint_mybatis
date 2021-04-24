package com.dbq.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisBasicConfig {

	@Autowired
	private RedisProperties redisProperties;

	@Bean("redisUtils")
	public RedisUtils getRedisUtil() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(RedisProperties.MAX_IDLE);
		config.setMaxTotal(RedisProperties.MAX_ACTIVE);config.setMaxWaitMillis(RedisProperties.MAX_WAIT);

		config.setTestOnBorrow(RedisProperties.TEST_ON_BORROW);

		Set<HostAndPort> nodes = new HashSet<>();

		String[] serverArray = redisProperties.getNodes().toString().trim().split(",");
		for(String hostAndPort : serverArray){
			String[] hostPortPair = hostAndPort.split(":");
			if (hostPortPair.length != 2) {
				throw new RuntimeException("redis properties in application.properties has an error!");
			}
			if (hostPortPair[0].matches(
					"((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))")) {
				try {
					int port = Integer.parseInt(hostPortPair[1]);
					nodes.add(new HostAndPort(hostPortPair[0], port));
				} catch (Exception e) {
					throw new RuntimeException("redis properties in application.properties has an error!", e);
				}
			} else {
				throw new RuntimeException("redis properties in application.properties has an illegal ip address!");
			}
		}

		return new RedisUtils(config, redisProperties.getHost(), Integer.valueOf(redisProperties.getPort()), RedisProperties.TIMEOUT,
				redisProperties.getAuth(), "true".equals(redisProperties.getUseCluster()), nodes);
	}
}