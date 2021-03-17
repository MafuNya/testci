public class DerPlusSub implements Der {
    private Der aa;
    private Der bb;
    private String ff;
    private String self;
    private String der;

    DerPlusSub(Der a, Der b, String f) {
        this.aa = a;
        this.bb = b;
        this.ff = f;
    }

    DerPlusSub() {}

    @Override
    public String getSelf() {
        return self = aa.getSelf() + ff + bb.getSelf();
    }

    public void set(Der a, Der b, String f) {
        this.aa = a;
        this.bb = b;
        this.ff = f;
    }

    @Override
    public String der() {
        if (aa.der().equals("0")) {
            if (bb.der().equals("0")) {
                der = "0";
            }
            else {
                der = bb.der();
            }
        }
        else if (bb.der().equals("0")) {
            der = aa.der();
        }
        else {
            der = "(" + aa.der() + "+" + bb.der() + ")";
        }
        return der;
    }
}
