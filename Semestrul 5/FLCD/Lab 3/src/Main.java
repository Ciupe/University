import domain.Scanner;
import domain.SymbolTable;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SymbolTable symbolTable = new SymbolTable();
        Scanner scanner = new Scanner(symbolTable, "Token.in");

        scanner.scan("p2.txt");
    }
}