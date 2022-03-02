package abstarctTest;

/**
 * @author lqb
 * @date 2022/3/2 15:54
 */
public class Son extends Father{
    @Override
    public void test() {
        System.out.println("son test");
    }

    public static void main(String[] args) {
        Son son = new Son();
        Father son1 = new Son();
        son.test();
        son.test1();
        son1.test();
        son1.test1();
    }
}
