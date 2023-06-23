package commands.clientcommands;

import collection.flatcollection.ServerCollection;
import serverlogic.outputchannel.ResponseArrayList;

public class Echo {
    ServerCollection serverCollection = new ServerCollection();
    ResponseArrayList responseArrayList = new ResponseArrayList();

    public void echo(String attribute){
        responseArrayList.addStringToResponseArrayList(attribute);
    }

    @Override
    public String toString() {
        return "echo";
    }
}
