package view;

import controller.Controller;
import exception.MyException;

import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        }
        catch (MyException | IOException | InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}