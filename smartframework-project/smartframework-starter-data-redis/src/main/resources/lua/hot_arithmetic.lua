if  redis.call("EXISTS", "HOT") == 0 or redis.call("EXISTS", "HOT_TIME") == 0 then
    return -1
end

-- 并集
redis.call("ZUNIONSTORE", "HOT_OLD", "2", "HOT", "HOT_OLD", "AGGREGATE", "MAX");

-- 参数
local num = tonumber(ARGV[1])
local time_past= ARGV[2]
local maxLoad = tonumber(ARGV[3])
local maxOldLen = tonumber(ARGV[4])

-- 释放容量
local old_len = redis.call("ZCARD", "HOT_OLD")
if  redis.call("INCR", "HOT_REINCARNATION") > maxLoad and old_len > maxOldLen then
    redis.call("ZREMRANGEBYRANK", "HOT_OLD", "0", old_len/2)
    redis.call("SET", "HOT_REINCARNATION","1")
end

-- top
local len = redis.call("ZCARD", "HOT_TIME")
if len > num then
    if redis.call("ZCOUNT", "HOT_TIME", time_past, "+inf") > num then
        redis.call("ZREMRANGEBYSCORE", "HOT_TIME", "0", time_past)
    else
        redis.call("ZREMRANGEBYRANK", "HOT_TIME", "0", len - num - 1)
    end
end
return redis.call("ZINTERSTORE", "HOT", "2", "HOT", "HOT_TIME", "AGGREGATE","MIN")