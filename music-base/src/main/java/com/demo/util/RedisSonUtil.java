package com.demo.util;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisSonUtil {

    //配置参考redisSon
    public static Config getRedisSonConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return config;
    }

    public static final String PLAYLIST_INFO_PREFIX = "playlist:"; //歌单存放在redis的前缀

    public static final String PLAYLIST_INFO_SUFFIX = ":info"; //歌单存放在redis的前缀

    public static final String PLAYLIST_INFO_LOCK = ":lock"; //专辑存放在redis的锁
}
