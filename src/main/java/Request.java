import java.io.Serializable;

/**
 * Created by serb on 13.01.15.
 */
public class Request implements Serializable{

    private Command command;
    private SomeObject obj;

    public Request(Command command, int hash) {
        this.command = command;
        this.hash = hash;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    private int hash;

    public Request(Command command, SomeObject obj) {
        this.command = command;
        this.obj = obj;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public SomeObject getObj() {
        return obj;
    }

    public void setObj(SomeObject obj) {
        this.obj = obj;
    }
}
