public class DerMul implements Der {
    private Der aa;
    private Der bb;
    private String akind;
    private String bkind;
    private String self;
    private String der;

    DerMul(Der a, Der b, String akind, String bkind) {
        this.aa = a;
        this.bb = b;
        this.akind = akind;
        this.bkind = bkind;
    }

    DerMul() {}

    @Override
    public String getSelf() {
        return self = aa.getSelf() + "*" + bb.getSelf();
    }

    @Override
    public String der() { //a*b.der+b*a.der
        if (aa.getSelf().equals("0") || bb.getSelf().equals("0")) { der = "0"; }
        else if (aa.der().equals("0")) { //aa=num
            if (bb.der().equals("0")) { der = "0"; }
            else {
                if (bb.der().equals("1")) { der = aa.getSelf(); } //num*x's der

                else if (bb.der().equals("-1")) { der = "-" + aa.getSelf(); }  //num*-x's der
                else { //num*func's der
                    if (aa.getSelf().equals("1")) { der = bb.der(); }
                    else if (aa.getSelf().equals("-1")) { der = "-" + bb.der(); }
                    else { der = aa.getSelf() + "*" + bb.der(); }
                }
            }
        }
        else if (aa.der().equals("1")) { // aa=x aa.der=1
            if (bb.der().equals("0")) { der = bb.getSelf(); } //x*num 's der
            else if (bb.der().equals("1")) { der = "2*x"; } //x*x's der
            else if (bb.der().equals("-1")) { der = "-2*x"; } //x*-x's der
            else if (bb.der().equals("2")) { der = "3*x*x"; } //x^3
            else { der = "(" + bb.getSelf() + "+x*" + bb.der() + ")"; } //x*func's der
        }
        else if (aa.der().equals("-1")) { // aa=-x aa.der=1
            if (bb.der().equals("0")) { //-x*num 's der
                if (bb.getSelf().equals("0")) { der = "0"; }
                else { der = "-" + bb.getSelf(); }
            }
            else if (bb.der().equals("1")) { der = "-2*x"; } //-x*x's der
            else if (bb.der().equals("-1")) {   der = "2*x"; } //-x*-x's der
            else if (bb.der().equals("2")) {   der = "-3*x*x"; } //-x*x**2
            else { der = "(-" + bb.getSelf() + "-x*" + bb.der() + ")"; } //-x*func's der
        }
        else {
            if (bb.der().equals("0")) { //func*num 's der
                if (bb.getSelf().equals("0")) { der = "0"; }
                else  if (bb.getSelf().equals("1")) { der = aa.der(); }
                else  if (bb.getSelf().equals("-1")) { der = "-" + aa.der(); }
                else { der = aa.der() + "*" + bb.getSelf(); }
            }
            else if (bb.der().equals("1")) { //func*x's der
                der = "(" + aa.der() + "*x" + "+" + aa.getSelf() + ")";
            }
            else if (bb.der().equals("-1")) {  //func*-x's der
                der = "(-" + aa.der() + "*x" + "-" + aa.getSelf() + ")";
            }
            else {
                der = "(" + aa.der() + "*" + bb.getSelf() + "+"
                        + aa.getSelf() + "*" + bb.der() + ")";
            }
        }
        return der;
    }
}