package com.cqupt.project.shop.task;

import com.cqupt.project.shop.common.Constant;
import com.cqupt.project.shop.redis.JedisClient;
import com.cqupt.project.shop.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author weigs
 * @date 2018/9/8 0008
 */
@Component
public class CloseOrderTask {
    private static final Logger log = LoggerFactory.getLogger(CloseOrderTask.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private JedisClient jedisClient;


    //    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV1() {
        int hour = 2;
        orderService.closeOrder(hour);
    }

    //    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动");
        //分布式锁
        long lockTimeout = Constant.LOCK_TIMEOUT;
        Long setnxResult = jedisClient.setnx(Constant.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //返回值为1，代表设置成功，获取锁
            closeOrder(Constant.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有获取到分布式锁：{}", Constant.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动");
        long lockTimeout = Constant.LOCK_TIMEOUT;
        Long setnxResult = jedisClient.setnx(Constant.CLOSE_ORDER_TASK_LOCK,
                String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //返回值为1，代表设置成功，获取锁
            closeOrder(Constant.CLOSE_ORDER_TASK_LOCK);
        } else {
            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = jedisClient.get(Constant.CLOSE_ORDER_TASK_LOCK);
            //判断是否是过期后第一个设置锁的线程
            if (lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)) {
                String getSetResult = jedisClient.getset(Constant.CLOSE_ORDER_TASK_LOCK,
                        String.valueOf(System.currentTimeMillis() + lockTimeout));
                if (getSetResult == null || StringUtils.equals(lockValueStr, getSetResult)) {
                    //获取到锁
                    closeOrder(Constant.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("没有获取到分布式锁{}", Constant.CLOSE_ORDER_TASK_LOCK);
                }
            } else {
                log.info("没有获取分布式锁{}", Constant.CLOSE_ORDER_TASK_LOCK);
            }
        }
    }

    private void closeOrder(String lockName) {
        jedisClient.expire(lockName, 50);
        log.info("获取{}", lockName);
        orderService.closeOrder(2);
        jedisClient.del(lockName);
        log.info("释放{}", lockName);
        log.info("======================");
    }
}
