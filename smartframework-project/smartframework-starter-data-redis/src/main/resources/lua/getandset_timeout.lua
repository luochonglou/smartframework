local result = redis.call("GETSET", KEYS[1], ARGV[1])
if not result then
   redis.call("EXPIRE",KEYS[1],ARGV[2])
end
return result