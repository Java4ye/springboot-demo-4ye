-- seckill0001:stock:voucher:1
local stock_key = "seckill0001:stock:voucher:" .. ARGV[1]

--  "seckill0001:order:" + voucherId + ":" + userId
local order_key = "seckill0001:order:" ..  ARGV[1] ..  ":" ..  ARGV[2]

if redis.call('exists',order_key) == 1 then
  -- 重复订单
  return -2
end

if redis.call('exists',stock_key) == 0 then
  -- 不存在
  return -1
end

local nums = tonumber(redis.call("get",stock_key))
if nums <= 0 then
    -- 库存不足
    return 0
end

-- 扣减库存
redis.call("incrby",stock_key,-1)

-- 设置订单
redis.call("set",order_key,ARGV[3])

return nums;