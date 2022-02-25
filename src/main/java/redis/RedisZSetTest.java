package redis;


import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.resps.Tuple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author lqb
 * @date 2022/2/25 9:35
 * redis实现延时队列测试
 */
public class RedisZSetTest {
    private static final JedisPool JEDIS_POOL = new JedisPool("localhost", 6379);
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(10);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Jedis getJedis() {
        return JEDIS_POOL.getResource();
    }

    public static void createOrders() {
        Jedis jedis = getJedis();
        for (int i = 0; i < 10; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 60);
            jedis.zadd("orderIds", calendar.getTimeInMillis(), StringUtils.join("00000", i + 1));
            System.out.println("创建订单：" + StringUtils.join("00000", i + 1) + " 当前时间：" + SIMPLE_DATE_FORMAT.format(new Date()) +
                    " 订单过期时间：" + SIMPLE_DATE_FORMAT.format(calendar.getTime()));
            System.out.println("一分钟后执行这些订单");
        }
    }

    public static void consumerDelayQueue(){
        System.out.println(Thread.currentThread().getName()+"开始执行延时消费");
        Jedis jedis = getJedis();
        while (true){
            long time = System.currentTimeMillis()+1;
            List<Tuple> orderIds = jedis.zrangeByScoreWithScores("orderIds",0,time);
            if (orderIds == null || orderIds.isEmpty()){
                try {
                    TimeUnit.MICROSECONDS.sleep(500);
                    //System.out.println("当前没有延时任务");
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (Tuple order : orderIds) {
                String element = order.getElement();
                long score = (long) order.getScore();
                System.out.println(Thread.currentThread().getName()+"执行任务，订单id:"+element+" 订单延时时间："+SIMPLE_DATE_FORMAT.format(score) + " 当前时间："+SIMPLE_DATE_FORMAT.format(new Date()));
                long result = jedis.zrem("orderIds", element);
                System.out.println(Thread.currentThread().getName()+"删除orderIds:"+element+"结果："+result);
            }
        }
    }

    static class ConsumerThread implements Runnable{
        @Override
        public void run() {
            try {
                COUNT_DOWN_LATCH.await();
                consumerDelayQueue();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName()+"执行延时消费异常");
            }
        }
    }

    public static void main(String[] args) {
        RedisZSetTest.createOrders();
        for (int i = 0; i < 10; i++) {
            new Thread(new ConsumerThread()).start();
            COUNT_DOWN_LATCH.countDown();
        }
    }

}
