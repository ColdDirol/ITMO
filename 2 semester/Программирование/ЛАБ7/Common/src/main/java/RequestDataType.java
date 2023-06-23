import java.io.Serializable;

public class RequestDataType implements Serializable {
    private String request = null;
    private CredentialsMapEntry credentialsMapEntry = null;

    public RequestDataType(String request, CredentialsMapEntry credentialsMapEntry) {
        this.request = request;
        this.credentialsMapEntry = credentialsMapEntry;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public CredentialsMapEntry getCredentialsMapEntry() {
        return credentialsMapEntry;
    }

    public void setCredentialsMapEntry(CredentialsMapEntry credentialsMapEntry) {
        this.credentialsMapEntry = credentialsMapEntry;
    }
}
