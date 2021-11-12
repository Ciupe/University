package view;

import model.FiniteAutomata;

import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        FiniteAutomata finiteAutomata = new FiniteAutomata("fa.in");

        boolean exit = false;
        while (!exit) {
            printMenu();

            int option;
            System.out.println("Choose option: ");
            if (scanner.hasNextInt())
                option = scanner.nextInt();
            else
                option = -1;


            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println(finiteAutomata.getStates());
                    break;
                case 2:
                    System.out.println(finiteAutomata.getAlphabet());
                    break;
                case 3:
                    System.out.println(finiteAutomata.getInitialState());
                    break;
                case 4:
                    System.out.println(finiteAutomata.getFinalStates());
                    break;
                case 5:
                    System.out.println(finiteAutomata.getTransitions());
                    break;
                case 6:
                    System.out.println(finiteAutomata.isDeterministic());
                    break;
                case 7: {

                    System.out.println(finiteAutomata.isAcceptedSequence("100000000000000000000001"));
                    break;
                }
                default:
                    System.out.println("Please enter a valid option.");
            }
        }
    }


    private void printMenu() {
        System.out.println("0. Exit program.");
        System.out.println("1. Show set of states.");
        System.out.println("2. Show alphabet.");
        System.out.println("3. Show initial state.");
        System.out.println("4. Show set of final states.");
        System.out.println("5. Show transitions.");
        System.out.println("6. Check if FA is deterministic.");
        System.out.println("7. Check if sequence is accepted.");
    }

}
