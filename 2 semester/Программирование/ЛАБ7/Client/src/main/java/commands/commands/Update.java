package commands.commands;

import collection.ElementConstructor;
import clientlogic.output.ConsoleOutputMode;

import java.util.ArrayList;
import java.util.Scanner;

public class Update {
    public ArrayList<String> update(Scanner scanner, ConsoleOutputMode MODE) {
        ElementConstructor elementConstructor = new ElementConstructor();
        return elementConstructor.construct(scanner, MODE);
    }

    @Override
    public String toString() {
        return "update";
    }
}
