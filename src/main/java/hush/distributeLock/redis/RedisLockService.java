package hush.distributeLock.redis;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 *
 * redis 实现分布式锁
 */

@Service
public class RedisLockService {


    private static Logger LOG = LoggerFactory.getLogger(RedisLockService.class);

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String DEFAULT_VALUE = "0";
    private static final String KEY_SET_MODE = "NX";
    private static final String KEY_EXPIRE_TIME_UNIT = "PX";




    /*@Autowired
    private GeneralCacheService redisCacheService;*/

    /**
     * 添加分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */

    public boolean acquireLock(String key, String value, long time, TimeUnit timeUnit) {
        Jedis jedis = redisCacheService.getServiceInstance();
        try {
            return StringUtils.isNotEmpty(
                    jedis.set(key, value, KEY_SET_MODE, KEY_EXPIRE_TIME_UNIT, timeUnit.toMillis(time)));
        } catch (Exception e) {
            LOG.error("Lock key error. key:{}", key, e);
            return false;
        } finally {
            redisCacheService.returnServiceInstance(jedis);
        }
    }



    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey,String requestId) {

        Jedis jedis = redisCacheService.getServiceInstance();
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


    public void del(String key) {
        Jedis jedis = redisCacheService.getServiceInstance();
        try {
            jedis.del(key);
        } catch (Exception e) {
            LOG.error("Del key error. key:{}", key, e);
        } finally {
            redisCacheService.returnServiceInstance(jedis);
        }
    }



    public boolean acquireLock(String key, long time, TimeUnit timeUnit) {
        return acquireLock(key, DEFAULT_VALUE, time, timeUnit);
    }



    public boolean validateCountLimit(String key, long countLimit, long time, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key)) {
            return true;
        }
        Jedis jedis = redisCacheService.getServiceInstance();
        try {
            long value = jedis.incr(key);
            if (value == 1) {
                jedis.expire(key, (int)timeUnit.toSeconds(time));
            }
            return value <= countLimit;
        } catch (Exception e) {
            LOG.error("Validate count limit error. key:{}, limit, time:{}, tUnit:{}", key, countLimit, time, timeUnit, e);
            return true;
        } finally {
            redisCacheService.returnServiceInstance(jedis);
        }
    }

    public boolean countIsExcess(String key, long countLimit, long time, TimeUnit timeUnit, Jedis jedis) {

        if (StringUtils.isEmpty(key)) {
            return true;
        }

        try {
            long value = jedis.incr(key);
            if (value == 1) {
                jedis.expire(key, (int) timeUnit.toSeconds(time));
            }
            return value > countLimit;
        } catch (Exception e) {
            LOG.error("Validate count limit error. key:{}, limit, time:{}, tUnit:{}", key, countLimit, time, timeUnit, e);
            return true;
        }
    }
}
