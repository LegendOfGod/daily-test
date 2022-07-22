package syncronizedandlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lqb
 * @date 2022/7/22 11:31
 * synchronized关键字
 * 是可重入的（线程获取某个锁后再次尝试获取）
 */
public class SynchronizedTest {

    private static final Logger logger = LoggerFactory.getLogger(SynchronizedTest.class);

    private String name;

    public SynchronizedTest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * sync普通方法 锁的是实例对象
     */
    public synchronized void sync1() throws InterruptedException {
        logger.info("线程:{},sync1 普通方法 name{} start",Thread.currentThread().getName(),name);
        Thread.sleep(1000);
        logger.info("线程:{},sync1 普通方法 name{} end",Thread.currentThread().getName(),name);
    }

    /**
     * sync静态方法 锁的是class对象
     */
    public static synchronized void sync2() throws InterruptedException {
        logger.info("线程:{},sync2 静态方法 start",Thread.currentThread().getName());
        Thread.sleep(1000);
        logger.info("线程:{},sync2 静态方法 end",Thread.currentThread().getName());
    }

    /**
     * sync代码块
     * synchronized (this) 实例对象锁
     * synchronized (SynchronizedTest.class) class对象锁
     * synchronized(a)  a = "aaa"可以锁住  a = new String("aaa")锁不住  synchronized (aaa.intern())可以
     * @throws InterruptedException
     */
    public void sync3() throws InterruptedException {
        String a = new String("aaa");
        synchronized (a.intern()){
            logger.info("线程:{},sync3 代码块方法 start",Thread.currentThread().getName());
            logger.info("线程:{},sync3 代码块方法 end",Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        SynchronizedTest test = new SynchronizedTest("对象");
        for (int i = 0; i < 10; i++) {
            new Thread("线程" + i){
                @Override
                public void run() {
                    try {
                        test.sync3();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
