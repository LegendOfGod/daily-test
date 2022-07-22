package jdk;

/**
 * @author lqb
 * @date 2022/6/15 11:33
 */
public class SynchronizedTest {
    private final String s;

    public SynchronizedTest(String s) {
        this.s = s;
    }

    public void test1(){
        test();
    }
    public void test(){
        synchronized (SynchronizedTest.class) {
            System.out.println(Thread.currentThread().getName()+"开始");
            System.out.println(this.s);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            new Thread(() -> {
                SynchronizedTest synchronizedTest = new SynchronizedTest(Integer.toString(finalI));
                synchronizedTest.test();
            },Integer.toString(i)).start();
        }
        System.out.println(1);
    }
}
