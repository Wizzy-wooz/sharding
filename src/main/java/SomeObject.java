import java.io.Serializable;
import java.util.Random;

/**
 * Created by serb on 13.01.15.
 */
public class SomeObject implements Serializable {

    private int i1;
    private int i2;

    @Override
    public String toString() {
        return "SomeObject{" +
                "i1=" + i1 +
                ", i2=" + i2 +
                '}';
    }

    SomeObject(int i1, int i2) {
        this.i1 = i1;
        this.i2 = i2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SomeObject objToSave = (SomeObject) o;

        if (i1 != objToSave.i1) return false;
        if (i2 != objToSave.i2) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = i1;
        result = 31 * result + i2;
        return result;
    }
}
