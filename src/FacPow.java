import java.math.BigInteger;

public class FacPow extends Factor {
    private BigInteger exp;

    public FacPow(String exp) { //x **
        super();
        if (exp.length() > 1) {
            this.exp = new BigInteger(exp.substring(3));
        }
        else {
            this.exp = BigInteger.ONE;
        }
    }

    public String getExp() {
        return exp.toString();
    }

    @Override
    public String der() {
        if (exp.equals(BigInteger.ZERO)) {
            return "0";
        }
        if (exp.equals(BigInteger.ONE)) {
            return "1";
        }
        if (exp.equals(new BigInteger("2"))) {
            return "2*x";
        }
        return exp.toString() + "*x**" + (exp.subtract(BigInteger.ONE).toString());
    }

    @Override
    public String getKind() {
        return "FacPow";
    }

    public String getSelf() {
        if (exp.equals(BigInteger.ZERO)) {
            return "1";
        }
        if (exp.equals(BigInteger.ONE)) {
            return "x";
        }
        if (exp.equals(BigInteger.ONE.add(BigInteger.ONE))) {
            return "x*x";
        }
        return "x**" + exp.toString();
    }
}
