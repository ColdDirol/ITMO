package annotations;

import java.lang.annotation.Documented;

public class Test {
    @ToChangeAnnotation
    private String myName = "Vladimir";

    @ToChangeAnnotation
    public String secondName = "Anton";

    public String getMyName() {
        return myName;
    }

    public String getSecondName() {
        return secondName;
    }
}
