package ds.examples.sockets.calculatormulti;

import java.util.Scanner;

public class OperationHandler {
    public String operation;
    public Scanner scanner;

    public OperationHandler(String op, Scanner scanner) {
        this.operation = op;
        this.scanner = scanner;
    }

    public String getResult(){
        return switch (this.operation) {
            case "add" -> add();
            case "sub" -> sub();
            case "mul" -> mul();
            case "div" -> div();
            case "len" -> len();
            case "cmp" -> cmp();
            case "cat" -> cat();
            case "delim" -> brek();
            default -> "Invalid operation";
        };
    }

    private String cmp(){
        String str1 = scanner.next();
        String str2 = scanner.next();
        Boolean value = false;

        if (str1.compareTo(str2) == 0)
            value = true;
        return "result:" + String.valueOf(value);
    }

    private String cat(){
        String str1 = scanner.next();
        String str2 = scanner.next();
        return "result:" + str1.concat(str2);
    }

    private String brek(){
        String str = scanner.next();
        String delim = scanner.next();
        String[] arrOfStr = str.split(delim);
        String result = "[";
        for (String a : arrOfStr)
            result += a + "],[";
        return "result:" + result;
    }

    private String len(){
        String str = scanner.next();
        return "result:" + String.valueOf(str.length());
    }

    private String div() {
        double x = Double.parseDouble(scanner.next());
        double y = Double.parseDouble(scanner.next());
        return "result:" + String.valueOf(x / y);
    }
    private String sub() {
        double x = Double.parseDouble(scanner.next());
        double y = Double.parseDouble(scanner.next());
        return "result:" + String.valueOf(x - y);
    }

    private String add() {
        double x = Double.parseDouble(scanner.next());
        double y = Double.parseDouble(scanner.next());
        return "result:" + String.valueOf(x + y);
    }

    private String mul(){
        double x = Double.parseDouble(scanner.next());
        double y = Double.parseDouble(scanner.next());
        return "result:" + String.valueOf(x * y);
    }
}
