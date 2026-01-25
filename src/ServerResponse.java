import java.io.Serializable;

//for sending responses from server to client, using polymorphism
public class ServerResponse implements Serializable {
    private ResponseType responseType;
    private Object payload;
    public ServerResponse(ResponseType responseType, Object payload) {
        this.responseType = responseType;
        this.payload = payload;
    }
    public ResponseType getResponseType() {
        return responseType;
    }
    public Object getPayload() {
        return payload;
    }
}
