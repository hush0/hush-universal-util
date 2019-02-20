package hush.common.service;

import redis.clients.jedis.Jedis;

import java.util.Set;

public interface GeneralCacheService {

    boolean set(String var1, String var2);

    boolean set(String var1, String var2, int var3);

    Jedis getServiceInstance();

    void returnServiceInstance(Jedis var1);

    String get(String var1, String var2);

    String get(String var1);

    byte[] get(byte[] var1);

    long delete(String var1);

    long incr(String var1);

    long decr(String var1);

    long sadd(String var1, String[] var2);

    boolean sismember(String var1, String var2);

    String set(byte[] var1, byte[] var2);

    long srem(String var1, String... var2);

    Set<String> smembers(String var1);
}
