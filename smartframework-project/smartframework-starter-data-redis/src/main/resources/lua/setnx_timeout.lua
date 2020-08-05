local result = redis.call("SET", KEYS[1], ARGV[1],"EX",ARGV[2],"NX")
if result then
  return 1
else
  return 0
end