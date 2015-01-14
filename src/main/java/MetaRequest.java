import java.io.Serializable;

/**
 * Created by serb on 13.01.15.
 */
public class MetaRequest implements Serializable {

    private int hash;

    MetaRequest(int hash){
        this.hash = hash;
    }


    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }
}
