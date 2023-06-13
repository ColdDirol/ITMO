package commands.commands;

import collection.ElementConstructor;
import clientlogic.output.ConsoleOutputMode;

import java.util.ArrayList;
import java.util.Scanner;

public class ReplaceIfLowe {
    public ArrayList<String> replaceIfLowe(Scanner scanner, ConsoleOutputMode MODE) {
        ElementConstructor elementConstructor = new ElementConstructor();
        return elementConstructor.construct(scanner, MODE);
    }

    @Override
    public String toString() {
        return "replace_if_lowe";
    }
}
