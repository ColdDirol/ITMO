package serverlogic.output;

public class PrepareToStop {
    private static boolean prepareToStop = false;

    public PrepareToStop () {
        prepareToStop = false;
    }

    public boolean isPrepareToStop() {
        return prepareToStop;
    }
    public void setPrepareToStop(boolean prepareToStop) {
        this.prepareToStop = prepareToStop;
    }
}
