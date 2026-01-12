import java.io.Serializable;

public class ClientRequest implements Serializable {
    RequestType requestType;
    Object payload;
    public ClientRequest(RequestType requestType, Object payload) {
        this.requestType = requestType;
        this.payload = payload;
    }
    public RequestType getRequestType() {
        return requestType;
    }
    public Object getPayload() {
        return payload;
    }
}
