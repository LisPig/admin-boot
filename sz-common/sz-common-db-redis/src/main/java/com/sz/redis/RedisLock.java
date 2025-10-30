package com.sz.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    private final StringRedisTemplate redisTemplate;

    public RedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }




    /**
     * 尝试获取锁
     * @param lockKey 锁的 key
     * @param expireTime 锁自动释放时间（秒）
     * @return 锁的唯一标识（用于释放锁），null 表示获取失败
     */
    public String tryLock(String lockKey, long expireTime) {
        String lockValue = UUID.randomUUID().toString();
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, lockValue, expireTime, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success) ? lockValue : null;
    }

    /**
     * 释放锁（安全释放：只有持有者才能释放）
     * @param lockKey 锁的 key
     * @param lockValue 锁的值（必须与加锁时一致）
     * @return 是否成功释放
     */
    public boolean releaseLock(String lockKey, String lockValue) {
        // 使用 Lua 脚本保证原子性：先判断 value 是否匹配，再删除
        String script = """
            if redis.call('get', KEYS[1]) == ARGV[1] then
                return redis.call('del', KEYS[1])
            else
                return 0
            end
            """;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
        return result != null && result == 1L;
    }

    public String tryLock(String lockKey) {
        return tryLock(lockKey, 30);
    }

    // ===== 回调式自动加锁释放 =====

    public void executeWithLock(String lockKey, Runnable task) {
        executeWithLock(lockKey, 30, task);
    }

    public void executeWithLock(String lockKey, long expireSeconds, Runnable task) {
        String lockValue = tryLock(lockKey, expireSeconds);
        if (lockValue == null) {
            throw new RuntimeException("Failed to acquire lock: " + lockKey);
        }
        try {
            task.run();
        } finally {
            releaseLock(lockKey, lockValue);
        }
    }
}
