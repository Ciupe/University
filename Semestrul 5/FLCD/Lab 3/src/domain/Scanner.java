package domain;
import adt.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scanner {
    SymbolTable symbolTable;
    ArrayList<String> tokens;
    ArrayList<Pair<String, Integer>> pif;
    private Boolean correct = true;

    public Scanner(SymbolTable symbolTable, String tokenFile) {
        this.symbolTable = symbolTable;
        pif = new ArrayList<>();
        readTokens(tokenFile);
    }

    private void readTokens(String tokenFile) {
        tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(tokenFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tokens.add(line.strip());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scan(String fileScanned) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fileScanned));

        String currentLine;
        int lineNumber = 0;

        while ((currentLine = reader.readLine()) != null){
            lineNumber++;
            if (isReservedWord(currentLine.strip()))
                pif.add(new Pair(currentLine.strip(), -1));
            else{
                currentLine = currentLine.strip();
                int lastPifIndex = 0;
                String newToken;

                for (int currentIndex = 0; currentIndex < currentLine.length(); ++currentIndex){
                    if (isSeparator(currentLine.charAt(currentIndex)) ||
                            (currentLine.charAt(currentIndex) == ' ' && !currentLine.substring(lastPifIndex, currentIndex).trim().contains("\"")))
                    {

                        newToken = currentLine.substring(lastPifIndex, currentIndex).trim();

                        if (newToken.length() != 0) {

                            if (isSeparator(newToken) || isReservedWord(newToken) || isOperator(newToken)) {
                                pif.add(new Pair(newToken, -1));
                            } else if (isConstant(newToken)) {
                                symbolTable.add(newToken);

                                pif.add(new Pair("constant", symbolTable.findIndexInArray(newToken)));

                            } else if (isIdentifier(newToken)) {
                                symbolTable.add(newToken);

                                pif.add(new Pair("id", symbolTable.findIndexInArray(newToken)));

                            }else {
                                System.out.println("Lexical error! Unidentified token on line: " + lineNumber + ": " + newToken);
                                correct = false;
                            }
                        }

                        if (currentLine.charAt(currentIndex) != ' ')
                            pif.add(new Pair(currentLine.charAt(currentIndex), -1));

                        lastPifIndex = currentIndex + 1; // now should throw an error if lastPifIndex == currentIndex (2 separators in a row)
                        currentIndex++;

                    }
                }
            }
        }

        writePif();
        writeSymbolTable();

        if (correct)
            System.out.println("Lexically correct!");
    }

    private void writePif() throws IOException {
        FileWriter outputfile = new FileWriter("PIF.out");

        for (int i = 0; i < pif.size(); ++i) {
            outputfile.write(pif.get(i) + "\n");
        }

        outputfile.close();
    }

    private void writeSymbolTable() throws IOException {
        FileWriter outputfile = new FileWriter("ST.out");

        for (int i = 0; i <= symbolTable.size(); ++i){
            if (symbolTable.getValue(i) != null)
                for (int j = 0; j < symbolTable.getValue(i).size(); ++j)
                    outputfile.write(symbolTable.getValue(i).get(j) + "\n");
        }

        outputfile.close();
    }

    private boolean isIdentifier(String checkedString){
        String regex = "^[_a-zA-Z]\\w*$";
        return checkedString.matches(regex);
    }

    private boolean isConstant(String checkedString){
        String stringRegex = "\"([^\"]*)\"";
        String numberRegex = "^[-]?[0-9]+";
        return checkedString.matches(stringRegex) || checkedString.matches(numberRegex) || checkedString.equals("true")
            || checkedString.equals("false");
    }

    private List<String> operators() {
        String[] operators = {"+", "-", "*", "/", "%", "==", "=", ">=", ">", "<=", "<", "!="};
        return Arrays.asList(operators);
    }

    private boolean isOperator(String token) {
        String[] operators = {"+", "-", "*", "/", "%", "==", "=", ">=", ">", "<=", "<", "!="};
        return Arrays.asList(operators).contains(token);
    }

    private List<String> separators(){
        String[] separators = {"(", ")", "[", "]", "{", "}", ":", ",", ";", " ", "\t", "\n"};
        return Arrays.asList(separators);
    }

    private boolean isSeparator(char token) {
        char[] separators = {'(', ')', '[', ']', '{', '}', ':', ',', ';', '\t', '\n'};
        for (int i = 0; i < separators.length; ++i)
            if (separators[i] == token)
                return true;
        return false;
    }

    private boolean isSeparator(String token) {
        String[] separators = {"(", ")", "[", "]", "{", "}", ":", ",", ";", " ", "\t", "\n"};
        return Arrays.asList(separators).contains(token);
    }

    private List<String> reservedWords(){
        String[] words = {"PROGRAM", "END_PROGRAM", "DECLARATION", "END_DECLARATION", "integer", "float", "char", "string",
                "bool", "array", "write", "read", "and", "or", "for", "do", "if", "while", "then", "else" };
        return Arrays.asList(words);
    }

    private boolean isReservedWord(String token) {
        String[] words = {"PROGRAM", "END_PROGRAM", "DECLARATION", "END_DECLARATION", "integer", "float", "char", "string",
                "bool", "array", "write", "read", "and", "or", "for", "do", "if", "while", "then", "else" };
        return Arrays.asList(words).contains(token);
    }

}