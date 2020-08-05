if  redis.call("EXISTS",KEYS[1]) == 0 then
    local result = redis.call("SETNX", KEYS[1], ARGV[1])
    if result == 1 then
        redis.call("EXPIRE",KEYS[1],ARGV[2])
    end
    return result
end
return -1