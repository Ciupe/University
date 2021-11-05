package model;

import adt.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiniteAutomata {
    private final List<String> states = new ArrayList<>();
    private final List<String> alphabet = new ArrayList<>();

    private final Map<Pair<String, String>, List<String>> transitions = new HashMap<>();
    private final List<String> finalStates = new ArrayList<>();
    private String initialState;
}
