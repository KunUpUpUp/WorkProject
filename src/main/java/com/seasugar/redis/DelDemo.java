package com.seasugar.redis;

import redis.clients.jedis.Jedis;

public class DelDemo {
    public static void main(String[] args) {
        // 连接 Redis 服务器
        Jedis jedis = new Jedis("localhost", 6379);

        jedis.del("user:zkp");
        jedis.setbit("user:zkp", 1, true);
        // 执行 UNLINK 命令删除指定的键
//        jedis.unlink("user");

        // 关闭连接
        jedis.close();
    }
}
