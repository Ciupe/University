public class Main {

    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.add("abcde");
        symbolTable.add("abcd");
        symbolTable.add("bcdea");

        Pair token1 = symbolTable.findIndexInArray("abcde");
        Pair token2 = symbolTable.findIndexInArray("bcdea");
        System.out.println(token1.getE1() + " " + token1.getE2());
        System.out.println(token2.getE1() + " " + token2.getE2());
    }
}
