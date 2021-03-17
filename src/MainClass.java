import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            FuncFactory funcFactory = new FuncFactory();
            String in = scanner.nextLine().replaceAll("[ \t]", "")
                    .replaceAll("sin\\(x\\)", "sin[x]")
                    .replaceAll("cos\\(x\\)", "cos[x]");
            Poly poly = funcFactory.getPoly(in);
            String out = funcFactory.der(poly);
            System.out.println(out);
        }
    }
}
