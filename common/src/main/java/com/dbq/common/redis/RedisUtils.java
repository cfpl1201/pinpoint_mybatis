package com.dbq.common.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author dbq
 *
 * * Modification  History:
 *
 *  * Date      Author  Version  Discription
 *
 *  * -----------------------------------------------------------------------------------
 *
 *  * 20200529  dbq      1.0      增加带有获取锁超时时间的分布式锁
 *
 * @see RedisUtils#tryLock(String, long, long)
 * @see RedisUtils#tryLock(String, String, long, long)
 * @see RedisUtils#lock(String, int, String)
 *
 */
//@Component("redisUtils")
public final class RedisUtils {

	private static Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	private static RedisOperateTemplate redisOperateTemplate;

	public RedisUtils(JedisPoolConfig config, String ip, Integer port, int timeout,
                      String auth, boolean cluster, Set<HostAndPort> nodes) {

		if (auth.equals("")) {
			auth = null;
		}

		redisOperateTemplate = new RedisOperateTemplate(config, ip, port, timeout, auth, cluster, nodes);
	}

	private static final int RELEASE_SUCCESS = 1;

	private static final String LOCK_SUCCESS = "OK";

	private static final String SET_IF_NOT_EXIST = "NX";

	private static final String SET_WITH_EXPIRE_TIME = "PX";

	private static String LOCK_PREFIX = "RSMS_LOCK_";

	private static String DEFAULT_LOCK_ID = UUID.randomUUID().toString();

	/**
	 * 等待锁时 循环睡眠的时间 毫秒
	 */
	private static final int LOCK_WAIT_SLEEP_TIME = 100;

	/**
	 * 锁有效时间（毫秒）
	 */
	private static final int DEFAULT_EXPIRE_TIME = 5000;

	/**
	 * 释放锁的Lua脚本
	 */
	private static final String RELEASE_LOCK_SCRIPT =
			"if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

	/**
	 * 原子性的获取并删除字符串的Lua脚本
	 */
	private static final String GET_DEL_SCRIPT =
			"local s=redis.call('get',KEYS[1]) if s then redis.call('del',KEYS[1]) end return s";

	/**
	 * 设置键值对，将字符串值 value 关联到 key
	 */
	public static String set(final String key, final String value) {
		return redisOperateTemplate.opreate(command -> command.set(key, value));
	}

	/**
	 * 设置键值对
	 *
	 * @param seconds 失效时间，以秒为单位
	 */
	public static String set(final String key, final int seconds, final String value) {
		return redisOperateTemplate.opreate(command -> command.setex(key, seconds, value));
	}

	/**
	 * 设置键值对
	 */
	public static Long setnx(final String key, final String value) {
		return redisOperateTemplate.opreate(command -> command.setnx(key, value));
	}

	/**
	 * 检查给定 key 是否存在
	 */
	public static Boolean exists(final String key) {
		return redisOperateTemplate.opreate(command -> command.exists(key));
	}

	/**
	 * 获取key对应的值
	 */
	public static String get(final String key) {
		return redisOperateTemplate.opreate(command -> command.get(key));
	}

	/**
	 * 获取key对应的值，并将该Key清除掉，此方法是原子的
	 */
	public static String getDel(final String key) {
		Object o = redisOperateTemplate.redisEval(GET_DEL_SCRIPT,
				Collections.singletonList(key), Collections.emptyList());
		return o == null ? null : o.toString();
	}

	/**
	 * 清除key对应的值
	 */
	public static Long del(final String key) {
		return redisOperateTemplate.opreate(command -> command.del(key));
	}

	/**
	 * 设置可以的过期时间
	 */
	public static Long expire(final String key, final int seconds) {
		return redisOperateTemplate.opreate(command -> command.expire(key, seconds));
	}

	public static Long ttl(final String key) {
		return redisOperateTemplate.opreate(command -> command.ttl(key));
	}

	/**
	 * 向哈希表里面添加数据
	 *
	 * @param key   哈希表名
	 * @param field 字段(键)
	 * @param value 值
	 */
	public static Long hset(final String key, final String field, final String value) {
		return redisOperateTemplate.opreate(command -> command.hset(key, field, value));
	}

	/**
	 * 获取哈希表里面的数据
	 *
	 * @param key   哈希表名
	 * @param field 字段
	 */
	public static String hget(final String key, final String field) {
		return redisOperateTemplate.opreate(command -> command.hget(key, field));
	}

	/**
	 * 获取哈希表里面的所有数据
	 *
	 * @param key 哈希表名
	 */
	public static Map<String, String> hgetAll(final String key) {
		return redisOperateTemplate.opreate(command -> command.hgetAll(key));
	}

	/**
	 * 删除哈希表里面的数据
	 *
	 * @param key   哈希表名
	 * @param field 字段
	 */
	public static Long hdel(final String key, final String field) {
		return redisOperateTemplate.opreate(command -> command.hdel(key, field));
	}

	/**
	 * 删除key所有数据
	 *
	 * @param key   哈希表名
	 */
	public static Long hdel(final String key) {
		return redisOperateTemplate.opreate(command -> command.del(key));
	}

	/**
	 * 判断哈希表里面的书否存在指定的字段
	 *
	 * @param key   哈希表名
	 * @param field 字段
	 */
	public static Boolean hexists(final String key, final String field) {
		return redisOperateTemplate.opreate(command -> command.hexists(key, field));
	}

	/**
	 * 添加一个元素到SortedSet
	 */
	public static Long zadd(final String key, final double score, final String member) {
		return redisOperateTemplate.opreate(command -> command.zadd(key, score, member));
	}

	/**
	 * 返回有序集 key 的基数
	 */
	public static Long zcard(final String key) {
		return redisOperateTemplate.opreate(command -> command.zcard(key));
	}

	/**
	 * 返回有序集 key 中，指定区间内的成员。
	 * 其中成员的位置按 score 值递增(从小到大)来排序。
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，
	 * 以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 */
	public static Set<Tuple> zrangeWithScores(final String key, final long start,
	                                          final long end) {
		return redisOperateTemplate.opreate(command -> command.zrangeWithScores(key, start, end));
	}

	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列
	 */
	public static Set<Tuple> zrangeByScoreWithScores(final String key, final double min,
											  final double max) {
		return redisOperateTemplate.opreate(command -> command.zrangeByScoreWithScores(key, min, max));
	}

	/**
	 * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员
	 * @return 移除记录数量
	 */
	public static Long zremrangeByScore(final String key, final double min, final double max) {
		return redisOperateTemplate.opreate(command -> command.zremrangeByScore(key, min, max));
	}

	public static Object eval(String luaScript, List<String> listKeys,
	                          List<String> listArgs) {
		return redisOperateTemplate.redisEval(luaScript, listKeys, listArgs);
	}

	/**
	 * 分布式锁
	 *
	 * @param key 待加锁的资源
	 */
	public static boolean lock(final String key) {
		return lock(key, DEFAULT_LOCK_ID, DEFAULT_EXPIRE_TIME, true);
	}

	/**
	 * 分布式锁
	 *
	 * @param key 待加锁的资源
	 */
	public static boolean tryLock(final String key) {
		return lock(key, DEFAULT_LOCK_ID, DEFAULT_EXPIRE_TIME, false);
	}

	/**
	 * 分布式锁
	 *
	 * @param key    待加锁的资源
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean lock(final String key, final long expire) {
		return lock(key, DEFAULT_LOCK_ID, expire, true);
	}

	/**
	 * 分布式锁
	 *
	 * @param key    待加锁的资源
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean tryLock(final String key, final long expire) {
		return lock(key, DEFAULT_LOCK_ID, expire, false);
	}

	/**
	 * 分布式锁
	 *
	 * @param key    待加锁的资源
	 * @param lockId 指定该所的ID
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean lock(final String key, final String lockId,
	                           final long expire) {
		return lock(key, lockId, expire, true);
	}

	/**
	 * 获取分布式锁，如果获取失败，抛指定信息的异常
	 *
	 * @param lockKey        Redis锁的Key
	 * @param timeout        锁的超时时间，超过该时间后自动释放锁
	 * @param failureMessage 发送异常时需要抛出的信息
	 */
	public static void lock(String lockKey, int timeout, String failureMessage) {
		try {
			RedisUtils.lock(lockKey, timeout);
		} catch (Exception e) {
			logger.error("failed to get distributed lock: " + lockKey, e);
			throw new RuntimeException(failureMessage);
		}
	}

	/**
	 * 分布式锁
	 *
	 * @param key    待加锁的资源
	 * @param lockId 指定该所的ID
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean tryLock(final String key, final String lockId,
	                              final long expire) {
		return lock(key, lockId, expire, false);
	}

	/**
	 * 分布式锁
	 *
	 * @param key    待加锁的资源
	 * @param lockId 指定该所的ID
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	private static boolean lock(final String key, final String lockId,
	                            final long expire, final boolean tryForever) {
		return redisOperateTemplate.opreate(command -> {
			boolean locked;
			do {
				locked = LOCK_SUCCESS.equals(command.set(LOCK_PREFIX + key,
						lockId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire));
				if (tryForever && !locked) {
					try {
						// 自旋
						TimeUnit.MILLISECONDS.sleep(LOCK_WAIT_SLEEP_TIME);
					} catch (InterruptedException ignore) {
						Thread.currentThread().interrupt();
					}
				}
			} while (tryForever && !locked);
			return locked;
		});
	}

	/**
	 * 该分布式锁增加了获取锁的超时时间，即在获取不到锁时会自旋获取锁，直到达到超时阈值(timeout)
	 *
	 * @param key    待加锁的资源
	 * @param timeout 获取锁的超时时间，单位：毫秒
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean tryLock(final String key, final long timeout,
	                              final long expire) {
		return lock(key, DEFAULT_LOCK_ID, timeout, expire);
	}

	/**
	 * 该分布式锁增加了获取锁的超时时间，即在获取不到锁时会自旋获取锁，直到达到超时阈值(timeout)
	 *
	 * @param key    待加锁的资源
	 * @param lockId 指定该所的ID
	 * @param timeout 获取锁的超时时间，单位：毫秒
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	public static boolean tryLock(final String key, final String lockId, final long timeout,
	                              final long expire) {
		return lock(key, lockId, timeout, expire);
	}

	/**
	 * 该分布式锁增加了获取锁的超时时间，即在获取不到锁时会自旋获取锁，直到达到超时阈值(timeout)
	 *
	 * @param key    待加锁的资源
	 * @param lockId 指定该所的ID
	 * @param timeout 获取锁的超时时间，单位：毫秒
	 * @param expire 锁的超时时间，单位：毫秒
	 */
	private static boolean lock(final String key, final String lockId, final long timeout,
	                            final long expire) {
		return redisOperateTemplate.opreate(command -> {
			boolean locked;
			long accuTime = 0L;
			do {
				locked = LOCK_SUCCESS.equals(command.set(LOCK_PREFIX + key,
						lockId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expire));
				if (accuTime < timeout && !locked) {
					try {
                        accuTime += LOCK_WAIT_SLEEP_TIME;
						// 自旋
						TimeUnit.MILLISECONDS.sleep(LOCK_WAIT_SLEEP_TIME);
					} catch (InterruptedException ignore) {
						Thread.currentThread().interrupt();
					}
				}
			} while (accuTime < timeout && !locked);
			return locked;
		});
	}

	/**
	 * 释放分布式锁
	 *
	 * @param key 待解锁的资源
	 */
	public static boolean unlock(final String key) {
		return unlock(key, DEFAULT_LOCK_ID);
	}

	/**
	 * 释放分布式锁
	 *
	 * @param key    待解锁的资源
	 * @param lockId 锁的ID
	 */
	public static boolean unlock(final String key, final String lockId) {
		Object o = redisOperateTemplate.redisEval(RELEASE_LOCK_SCRIPT,
				Collections.singletonList(LOCK_PREFIX + key),
				Collections.singletonList(lockId));
		return (o instanceof Number) && RELEASE_SUCCESS == ((Number) o).intValue();
	}

	private interface RedisOperation<T> {
		/**
		 * 通用的redis操作
		 *
		 * @param command jedis command
		 * @return redis操作的返回值
		 */
		T opreate(JedisCommands command);
	}

	private static class RedisOperateTemplate {

		private JedisPool jedisPool = null;

		private JedisCluster jedisCluster = null;

		private boolean cluster;

		public RedisOperateTemplate(JedisPoolConfig config, String ip, Integer port, int timeout,
		                            String auth, boolean cluster, Set<HostAndPort> nodes) {
			this.cluster = cluster;
			//支持集群模式
			if (cluster) {
				GenericObjectPoolConfig genericConfig = new GenericObjectPoolConfig();
				config.setMaxIdle(RedisProperties.MAX_IDLE);
				config.setMaxTotal(RedisProperties.MAX_ACTIVE);
				config.setMaxWaitMillis(RedisProperties.MAX_WAIT);
				config.setTestOnBorrow(RedisProperties.TEST_ON_BORROW);
				jedisCluster = new JedisCluster(nodes, RedisProperties.MAX_WAIT, RedisProperties.TIMEOUT, 6, auth, genericConfig);
			} else {
				jedisPool = new JedisPool(config, ip, port, timeout, auth);
			}
		}


		/**
		 * 用于抽取redis操作的模板方法
		 */
		<T> T opreate(RedisOperation<T> operation) {
			if (cluster) {
				try {
					return operation.opreate(jedisCluster);
				} catch (Exception e) {
					logger.error("redis opreate error: ", e);
					throw e;
				}
			} else {
				Jedis jedis = null;
				try {
					jedis = jedisPool.getResource();
				} catch (Exception e) {
					logger.error("get Jedis resource caught an error!", e);
				}
				if (jedis != null) {
					try {
						return operation.opreate(jedis);
					} catch (Exception e) {
						logger.error("redis opreate error : ", e);
						throw e;
					} finally {
						jedis.close(); // 释放jedis资源
					}
				}
			}
			return null;
		}

		/**
		 * nodeFlag true 每个节点都执行 false 只在其中一个节点执行
		 * redis 执行 lua脚本
		 */
		Object redisEval(String luaScript, List<String> listKeys, List<String> listArgs) {
			if (cluster) {
				try {
					return jedisCluster.eval(luaScript, listKeys, listArgs);
				} catch (Exception e) {
					logger.error("redis eval error : ", e);
					throw e;
				}
			} else {
				Jedis jedis = null;
				try {
					jedis = jedisPool.getResource();
				} catch (Exception e) {
					logger.error("get Jedis resource caught an error!", e);
				}
				if (jedis != null) {
					try {
						return jedis.eval(luaScript, listKeys, listArgs);
					} catch (Exception e) {
						logger.error("redis eval error : ", e);
						throw e;
					} finally {
						jedis.close(); // 释放jedis资源
					}
				}
			}
			return null;
		}
	}

}
