package serverlogic.outputchannel;

import java.io.*;

public class IOHelperClass {
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public IOHelperClass(BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        this.bufferedWriter = bufferedWriter;
        this.bufferedReader = bufferedReader;
    }

    public void closeSockets() {
        try {
            bufferedWriter.close();
            bufferedReader.close();
        } catch (IOException e) {

        }
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
}
