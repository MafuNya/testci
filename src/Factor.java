public class Factor implements Der {
    public Factor() {
    }

    @Override
    public String der() {
        return null;
    }

    public String getKind() {
        return "Factor";
    }

    @Override
    public String getSelf() {
        return "factor";
    }
}