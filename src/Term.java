import java.util.ArrayList;
import java.util.HashSet;

public class Term implements Der {
    private String term;
    private String termF;
    private HashSet<Factor> factors = new HashSet<>();
    private Der tree = new DerMul();

    public Term(String term, String termF, HashSet<Factor> factors) {
        this.term = term;
        this.termF = termF;
        this.factors = factors;
    }

    @Override
    public String der() {
        ArrayList<Factor> tt = new ArrayList<>(factors);
        ArrayList<Der> ww = new ArrayList<>();
        if (tt.size() == 1) {
            if (termF.equals("-")) {
                return "(" + termF + tt.get(0).der() + ")";
            }
            return tt.get(0).der();
        }
        else {
            ww.add(new DerMul(tt.get(0), tt.get(1), "DerMul", tt.get(1).getKind()));
            for (int i = 1; i < tt.size() - 1; i++) {
                ww.add(new DerMul(ww.get(i - 1), tt.get(i + 1), "DerMul", tt.get(i + 1).getKind()));
            }
            tree = ww.get(ww.size() - 1);//head
            String de = tree.der();
            if (termF.equals("-")) {
                return "(" + termF + de + ")";
            }
            return de;
        }
    }

    @Override
    public String getSelf() {
        return term;
    }

    public void setTermF(String polyF) {
        if (polyF.equals(termF)) {
            termF = "+";
        }
        else {
            termF = "-";
        }
    }
}
