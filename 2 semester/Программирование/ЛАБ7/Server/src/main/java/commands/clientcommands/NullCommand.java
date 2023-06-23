package commands.clientcommands;

import serverlogic.outputchannel.ResponseArrayList;

public class NullCommand {
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void nullCommand(){
        System.out.println("next");
    }

    @Override
    public String toString() {
        return "null_command";
    }
}
