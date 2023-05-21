package commands.commands;

import serverlogic.ResponseArrayList;

public class NullCommand {
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void nullCommand() {
        int answer = (int) (Math.random() * 2);
        if(answer == 1) {
            System.out.println("next");
        } else System.out.println("wait");
    }

    @Override
    public String toString() {
        return "super.toString()";
    }
}
