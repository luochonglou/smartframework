local result = redis.call("INCRBY", KEYS[1], ARGV[1])
if tostring(result) == ARGV[1] then
   redis.call("EXPIRE",KEYS[1],ARGV[2])
end
return result