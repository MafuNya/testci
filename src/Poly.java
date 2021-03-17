import java.util.ArrayList;
import java.util.HashSet;

public class Poly implements Der {
    private String polyF;
    private HashSet<Term> terms = new HashSet<>();
    private Der tree = new DerPlusSub();
    private String poly;

    public Poly(String poly, String polyF, HashSet<Term> terms) {
        this.poly = poly;
        this.polyF = polyF;
        this.terms = terms;
    }

    @Override
    public String getSelf() {
        return poly;
    }

    @Override
    public String der() {
        ArrayList<Der> tt = new ArrayList<>(terms);
        ArrayList<Der> ww = new ArrayList<>();
        if (tt.size() == 1) {
            return tt.get(0).der();
        } else {
            ww.add(new DerPlusSub(tt.get(0), tt.get(1), "+"));
            for (int i = 1; i < tt.size() - 1; i++) {
                ww.add(new DerPlusSub(ww.get(i - 1), tt.get(i + 1), "+"));
            }
            tree = ww.get(ww.size() - 1);//head
            String de = tree.der();
            /*return de.replaceAll("\\*\\*", "^") //** -> ^
                    .replaceAll("0\\*.*\\+", "0+")// 0*...... + -> ""
                    .replaceAll("\\+.*\\*0.*+", "+0")// +......*0
                    .replaceAll("0\\*.*-", "")
                    .replaceAll("0\\*.*-", "")
                    .replaceAll("0\\*.*-", "")
                    .replaceAll("0\\*.*-", "")
                    .replaceAll("0\\*.*-", "")
                    ; // 0*...... - -> ""
            */
            return de;
        }
    }
}