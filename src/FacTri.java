import java.math.BigInteger;

public class FacTri extends Factor {
    private String type;
    private BigInteger exp;
    private String self;

    public FacTri(String self) {
        this.self = self;
        this.type = self.substring(0, 3);
        if (self.length() > 6) {
            this.exp = new BigInteger(self.substring(8));
        }
        else {
            this.exp = BigInteger.ONE;
        }
    }

    @Override
    public String der() {
        if (exp.equals(BigInteger.ZERO)) {
            return "0";
        } else {
            if (type.equals("sin")) {
                if (exp.equals(BigInteger.ONE)) {
                    return "cos[x]";
                }
                if (exp.equals(BigInteger.valueOf(2))) {
                    return "2*cos[x]*sin[x]";
                }
                return exp.toString() + "*cos[x]*sin[x]**"
                        + exp.subtract(BigInteger.ONE).toString();
            } else {
                if (exp.equals(BigInteger.ONE)) {
                    return "-sin[x]";
                }
                if (exp.equals(BigInteger.valueOf(2))) {
                    return "-2*sin[x]*cos[x]";
                }
                return "-" + exp.toString() + "*sin(x)*cos[x]**"
                        + exp.subtract(BigInteger.ONE).toString();
            }
        }
    }

    @Override
    public String getKind() {
        return "FacTri";
    }

    @Override
    public String getSelf() {
        if (exp.equals(BigInteger.ZERO)) {
            return "1";
        } else {
            if (type.equals("sin")) {
                if (exp.equals(BigInteger.ONE)) {
                    return "sin[x]";
                }
                return "sin[x]**"
                        + exp.toString();
            } else {
                if (exp.equals(BigInteger.ONE)) {
                    return "cos[x]";
                }
                return  "cos[x]**"
                        + exp.toString();
            }
        }
    }
}
