import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncFactory {
    private ArrayList<FacPoly> polyFactors = new ArrayList<>();
    private ArrayList<String> polyFactorSelf = new ArrayList<>();
    private Integer getPolyFacCnt = 0;
    private Integer selfCnt = 0;
    private Integer getSelfCnt = 0;
    private String num = "([+-]?\\d+)";
    private String exp = "(\\*\\*" + num + ")?";
    private String pow = "(x" + exp + ")";
    private String sin = "(sin" + "\\[" + "x" + "\\]" + exp + ")";
    private String cos = "(cos" + "\\[" + "x" + "\\]" + exp + ")";
    private String tri = "(" + sin + "|" + cos + ")";
    private String factor = "(" + pow + "|" + tri + "|" + num  + "|" + "@)";
    private String term = "([+-]?" + "(" + factor + ")"
            + "(\\*" + factor + ")*)";
    private String poly = "((?<polyF>[+-]?)" + "(" + term + "))"
            + "([+-]" + term + ")*";

    private Pattern p0 = Pattern.compile(poly);

    public FuncFactory() {
    }

    public Poly getPoly(String funcIn) {
        String func = parseKh(funcIn); // simplify ((())) and remove (polyFac) to @
        func = replaceKh(func);
        HashSet<Term> terms = new HashSet<>();
        String polyF = "+";
        Matcher mpoly = p0.matcher(func);
        if (mpoly.find()) {
            String po = mpoly.group(0);
            final String poself = po;
            //System.out.println("find poly: " + po);
            if (mpoly.group("polyF") != null
                    && !mpoly.group("polyF").equals("")) {
                polyF = mpoly.group("polyF");
                po = po.substring(1);
            }

            String term1 = "((?<termF>[+-]?)" + "(" + factor + ")"
                    + "(\\*" + factor + ")*)";
            Pattern pterm1 = Pattern.compile(term1);
            Matcher mterm1 = pterm1.matcher(po);
            Integer cnt = 1;
            // first +- force to be polyF then first term won't cnt twice
            if (mterm1.find()) {
                Term tmp = getTerm(cnt, mterm1);
                tmp.setTermF(polyF);
                terms.add(tmp);
                cnt++;
            }

            while (mterm1.find()) {
                Term tmp = getTerm(cnt, mterm1);
                terms.add(tmp);
                cnt++;
            }
            return new Poly(poself, polyF, terms);
        }
        return null;
    }

    public Term getTerm(Integer cnt, Matcher mterm) {
        HashSet<Factor> factors = new HashSet<>();
        String te = mterm.group(0);
        String teself = te;
        //System.out.println("find term" + cnt + ": " + te);
        String termF = "+";
        if (mterm.group("termF") != null
                && !mterm.group("termF").equals("")) {
            termF = mterm.group("termF");
            te = te.substring(1);
        }

        String factor1 = "(?<pow>" + pow + ")|" + "(?<tri>" + tri + ")|"
                        + "(?<num>" + num + ")|" + "(?<polyfac>@)";
        Pattern pfac1 = Pattern.compile(factor1);
        Matcher mfac1 = pfac1.matcher(te);
        Integer cn = 1;
        // first +- force to be polyF then first term won't cnt twice
        while (mfac1.find()) {
            Factor tmp = getFactor(cn, mfac1);
            factors.add(tmp);
            cn++;
        }
        return new Term(teself, termF, factors);
    }

    public Factor getFactor(Integer cn, Matcher mfac) {
        String fa = mfac.group(0);
        //System.out.println("find fac" + cn + ": " + fa);
        if (mfac.group("pow") != null) {
            if (mfac.group("pow").contains("**")) {
                String ex = mfac.group("pow"); //x**
                return new FacPow(ex);
            }
            else {
                return new FacPow("1");
            }
        }
        else if (mfac.group("tri") != null) {
            return new FacTri(mfac.group("tri"));
        }
        else if (mfac.group("num") != null) {
            return new FacNum(mfac.group("num"));
        }
        else if (mfac.group("polyfac") != null) {
            return getOnePolyFacOut();
        }
        System.out.println("wf!!");
        return null;
    }

    public String parseKh(String ins) { //kill (())
        String in = ins;
        int cnt1 = 0;
        int cnt2 = 0;
        int i = 0;
        ArrayList<Integer> index1 = new ArrayList<>();
        TreeMap<Integer, Integer> match = new TreeMap();
        while (i != in.length()) {
            if (in.charAt(i) == '(') {
                index1.add(i);
                cnt1++;
            }
            if (in.charAt(i) == ')') {
                for (int j = i - 1; j >= 0; j--) {
                    if (in.charAt(j) == '(' && !match.containsKey(j)) {
                        match.put(j, i);
                        break;
                    }
                } cnt2++;
            }
            if (in.charAt(i) == ')' && cnt1 == cnt2 && index1.size() >= 1) {
                int k = -1;
                for (Integer j : match.keySet()) {
                    if (match.get(j) == i) {
                        k = j;
                        break;
                    }
                }
                if (in.charAt(i - 1) == ')' && in.charAt(k + 1) == '(') {
                    if (match.get(k + 1) == i - 1) {
                        if (i == in.length() - 1) { //")" located at end
                            if (k == 0) { in = in.substring(1, i); }  //"(" at head
                            else { //"(" not at head  ...(...)
                                in = in.substring(0, k)
                                        + in.substring(k + 1, i);
                            }
                        } else { //")" not at end
                            if (k == 0) { //"(" at head  (...)..
                                in = in.substring(1, i) + in.substring(i + 1);
                            } else { // ...(...)..
                                in = in.substring(0, k)
                                        + in.substring(k + 1, i)
                                        + in.substring(i + 1);
                            }
                        }
                        i = -1;
                    }
                    index1 = new ArrayList<>();
                    match = new TreeMap<>();
                    cnt1 = 0;
                    cnt2 = 0;
                }
            } i++;
        }
        return in;
    }

    public FacPoly getOnePolyFacOut() {
        getPolyFacCnt++;
        return polyFactors.get(getPolyFacCnt - 1);
    }

    public String replaceKh(String ins) {
        String in = ins;
        int cnt1 = 0;
        int cnt2 = 0;
        int i = 0;
        ArrayList<Integer> index1 = new ArrayList<>();
        TreeMap<Integer, Integer> match = new TreeMap();
        while (i != in.length()) {
            if (in.charAt(i) == '(') {
                index1.add(i);
                cnt1++;
            }
            if (in.charAt(i) == ')') {
                for (int j = i - 1; j >= 0; j--) {
                    if (in.charAt(j) == '(' && !match.containsKey(j)) {
                        match.put(j, i);
                        break;
                    }
                } cnt2++;
            }
            if (in.charAt(i) == ')' && index1.size() >= 1) {
                int k = -1;
                for (Integer j : match.keySet()) {
                    if (match.get(j) == i) {
                        k = j;
                        break;
                    }
                }
                if (k == 0 || k - 1 >= 0 && in.charAt(k - 1) != 's' && in.charAt(k - 1) != 'n') {
                    //not "sin( cos(" but "*|+ (...)"
                    String newIn = in.substring(k + 1, i);
                    if (newIn.contains("@")) {
                        while (newIn.contains("@")) {
                            newIn = newIn.replace("@", polyFactorSelf.get(selfCnt));
                            selfCnt++;
                        }
                    }
                    //System.out.println(newIn);
                    polyFactorSelf.add(newIn);
                    polyFactors.add(new FacPoly(in.substring(k + 1, i), this
                            , polyFactorSelf.get(getSelfCnt)));
                    getSelfCnt++;
                    in = in.substring(0, k) + "@" + in.substring(i + 1);
                    i = -1;
                    //System.out.println(in);
                    index1 = new ArrayList<>();
                    match = new TreeMap<>();
                    cnt1 = 0;
                    cnt2 = 0;
                }
            }
            i++;
        }
        return in;
    }

    public String killOutsideKh(String ins) {
        String in = ins;
        int i = 0;
        ArrayList<Integer> index1 = new ArrayList<>();
        TreeMap<Integer, Integer> match = new TreeMap();
        while (i != in.length()) {
            if (in.charAt(i) == '(') {
                index1.add(i);
            }
            if (in.charAt(i) == ')') {
                for (int j = i - 1; j >= 0; j--) {
                    if (in.charAt(j) == '(' && !match.containsKey(j)) {
                        match.put(j, i);
                        break;
                    }
                }
            }
            i++;
        }
        int tmp = 0;
        int tmpLast = in.length() - 1;
        while (in.charAt(0) == '(' && in.charAt(in.length() - 1) == ')'
                && match.get(tmp) == tmpLast) {
            in = in.substring(1, in.length() - 1);
            tmp++;
            tmpLast--;
        }
        return in;
    }

    public String simplify(String s) {
        String oo = s.replaceAll("sin\\(x\\)", "sin[x]")
                .replaceAll("cos\\(x\\)", "cos[x]");
        String facSim = "(\\(" + pow + "\\)|\\(" + tri + "\\)|\\(" + num  + "\\))";
        //make (factor) -> factor
        Pattern psim = Pattern.compile(facSim);
        Matcher msim = psim.matcher(oo);
        while (msim.find()) {
            oo = oo.replace(msim.group(0), msim.group(0).substring(1, msim.group(0).length() - 1));
        }
        oo = killOutsideKh(oo);
        return oo.replaceAll("\\[", "(")
                .replaceAll("\\]", ")")
                .replaceAll("--", "+")
                .replaceAll("\\+-", "-")
                .replaceAll("-\\+", "-")
                .replaceAll("\\(1\\+1\\)", "2")
                .replaceAll("\\(1\\+4\\)", "5")
                .replaceAll("\\(4\\+1\\)", "5");
    }

    public String der(Poly poly) {
        String out = poly.der();
        return simplify(out);
    }
}