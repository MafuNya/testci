import org.omg.PortableInterceptor.INACTIVE;

public class Test {
    private Integer a;
    private Integer b;

    public Integer add() {
        return a+b;
    }

    public Integer sub() {
        return a-b;
    }

    public Test(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }
}
