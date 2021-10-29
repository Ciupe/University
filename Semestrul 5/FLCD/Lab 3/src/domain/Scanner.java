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
        while ((currentLine = reader.readLine()) != null){
            if (isReservedWord(currentLine.strip()))
                pif.add(new Pair(currentLine.strip(), -1));
            else{
                currentLine = currentLine.strip();
                int lastPifIndex = 0;
                String newToken;

                for (int currentIndex = 0; currentIndex < currentLine.length(); ++currentIndex){
                    if (isSeparator(currentLine.charAt(currentIndex))){
                        newToken = currentLine.substring(lastPifIndex, currentIndex);

                        if (isSeparator(newToken) || isReservedWord(newToken) || isOperator(newToken)){
                            pif.add(new Pair(newToken, -1));
                        }

                        else if (isIdentifier(newToken)){
                            symbolTable.add(newToken);
                            pif.add(new Pair("id", symbolTable.findIndexInArray(newToken)));
                        }
                        


                        lastPifIndex = currentIndex + 1; // now should throw an error if lastPifIndex == currentIndex (2 separators in a row)
                        currentIndex++;
                    }
                }
            }
        }

    }

    private void writePif() throws IOException {
        FileWriter outputfile = new FileWriter("PIF.out");

        for (int i = 0; i < pif.size(); ++i) {
            outputfile.write(pif.get(i) + "\n");
        }

        outputfile.close();
    }

    private void writeST() throws IOException {
        FileWriter outputfile = new FileWriter("SymbolTable.out");

        for (int i = 0; i < symbolTable.size(); ++i){
            if (symbolTable.getValue(i) != null)
                for (int j = 0; j < symbolTable.getValue(i).size(); ++j)
                    outputfile.write(symbolTable.getValue(i).get(j));
        }

        outputfile.close();
    }

    private boolean isIdentifier(String checkedString){
        String regex = "^[_a-zA-Z]\\w*$";
        return checkedString.matches(regex);
    }

    private boolean isConstant(String checkedString){
        String regex = "\"([^\"]*)\"";
        return checkedString.matches(regex);
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
        String[] separators = {"(", ")", "[", "]", "{", "}", ":", ",", ";", " ", "\t", "\n"};
        return Arrays.asList(separators).contains(token);
    }

    private boolean isSeparator(String token) {
        String[] separators = {"(", ")", "[", "]", "{", "}", ":", ",", ";", " ", "\t", "\n"};
        return Arrays.asList(separators).contains(token);
    }

    private List<String> reservedWords(){
        String[] words = {"PROGRAM", "END_PROGRAM", "DECLARATION", "END_DECLARATION", "integer", "float", "char", "string",
                "array", "write", "read", "and", "or", "for", "do", "if", "while", "then" };
        return Arrays.asList(words);
    }

    private boolean isReservedWord(String token) {
        String[] words = {"PROGRAM", "END_PROGRAM", "DECLARATION", "END_DECLARATION", "integer", "float", "char", "string",
                "array", "write", "read", "and", "or", "for", "do", "if", "while", "then" };
        return Arrays.asList(words).contains(token);
    }

}