import java.io.Serializable;

/**
 * Created by serb on 13.01.15.
 */
public class Response implements Serializable{
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public Response(Status status) {
        this.status = status;
    }

    private Status status;

    public Response(Status status, SomeObject obj) {
        this.obj = obj;
        this.status = status;
    }

    public SomeObject getObj() {

        return obj;
    }

    public void setObj(SomeObject obj) {
        this.obj = obj;
    }

    private SomeObject obj;
}
