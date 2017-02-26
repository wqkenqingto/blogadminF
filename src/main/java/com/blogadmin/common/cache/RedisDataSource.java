/*
 * Copyright 2016 gaoshou360.com All right reserved. This software is the
 * confidential and proprietary information of gaoshou360.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with gaoshou360.com .
 */
package com.blogadmin.common.cache;

import redis.clients.jedis.ShardedJedis;


public interface RedisDataSource {

    /**
     * 取得redis的客户端，可以执行命令了
     * 
     * @return
     */
    public abstract ShardedJedis getRedisClient();

    /**
     * 将资源返还给pool
     * 
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);

    /**
     * 出现异常后，将资源返还给pool （其实不需要第二个方法）
     * 
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
