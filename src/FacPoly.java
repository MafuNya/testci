public class FacPoly extends Factor {
    private Poly poly;
    private String polyself;

    public FacPoly(String po, FuncFactory funcFactory, String self) {
        poly = funcFactory.getPoly(po);
        polyself = "(" + self + ")";
    }

    @Override
    public String der() {
        return poly.der();
    }

    @Override
    public String getKind() {
        return "FacPoly";
    }

    @Override
    public String getSelf() {
        return polyself;
    }
}
