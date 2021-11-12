package model;

import adt.Pair;

import java.io.File;
import java.util.*;

public class FiniteAutomata {
    private final List<String> states = new ArrayList<>();
    private final List<String> alphabet = new ArrayList<>();
    private final Map<Pair<String, String>, List<String>> transitions = new HashMap<>();
    private final List<String> finalStates = new ArrayList<>();
    private String initialState;

    public FiniteAutomata(String fileName){
        read(fileName);
    }

    private void read(String fileName){
        try {
            int option = 0;
            File file = new File(fileName);
            Scanner fileReader = new Scanner(file);
            String line;
            while (fileReader.hasNextLine()){
                line = fileReader.nextLine().strip();
                System.out.println(line);

                if (line.equals("Q"))
                    option = 1;

                else if (line.equals("Σ"))
                    option = 2;

                else if (line.equals("q0"))
                    option = 3;

                else if (line.equals("F"))
                    option = 4;

                else if (line.equals("δ"))
                    option = 5;

                else {
                    switch(option){
                        case 1:
                            states.add(line);
                            break;
                        case 2:
                            alphabet.add(line);
                            break;
                        case 3:
                            initialState = line;
                            break;
                        case 4:
                            finalStates.add(line);
                            break;
                        case 5: {
                            Pair<String, String> newPair = new Pair<>(String.valueOf(line.charAt(1)), String.valueOf(line.charAt(4)));
                            if (transitions.get(newPair) == null)
                            {
                                transitions.put(newPair, new ArrayList<>());
                                transitions.get(newPair).add(String.valueOf(line.charAt(9)));
                            }
                            else
                                transitions.get(newPair).add(String.valueOf((line.charAt(9))));
                            break;
                        }
                    }
                }
            }
        }

        catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean isAcceptedSequence(String sequence){
        if (!isDeterministic())
            return false;

        String currentState = getInitialState();

        for (int i = 0; i < sequence.length(); ++i){
            var transition = String.valueOf(sequence.charAt(i));
            var newPair = new Pair<>(currentState, transition);

            boolean foundMatch = false;

            var keySet = transitions.keySet();
            for (Pair<String, String> pair : keySet)
                if (pair.equals(newPair)){
                    foundMatch = true;
                    currentState = transitions.get(pair).get(0);
                }

            if (!foundMatch)
                return false;
        }

        if (finalStates.contains(currentState))
            return true;
        return false;
    }

    public boolean isDeterministic(){
        for (Pair<String, String> key : transitions.keySet())
            if (transitions.get(key).size() > 1)
                return false;

        return true;
    }

    public List<String> getStates(){
        return this.states;
    }

    public List<String> getAlphabet(){
        return this.alphabet;
    }

    public Map<Pair<String, String>, List<String>> getTransitions(){
        return this.transitions;
    }

    public String getInitialState(){
        return this.initialState;
    }

    public List<String> getFinalStates(){
        return this.finalStates;
    }

}
