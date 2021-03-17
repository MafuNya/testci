import java.math.BigInteger;

public class FacNum extends Factor {
    private BigInteger num;

    public FacNum(String num) {
        super();
        this.num = new BigInteger(num);
    }

    @Override
    public String der() {
        return "0";
    }

    @Override
    public String getKind() {
        return "FacNum";
    }

    @Override
    public String getSelf() {
        return num.toString();
    }
}
