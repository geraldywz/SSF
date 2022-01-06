package tfip.ssf.d12.model;

import java.io.Serializable;

public class Generate implements Serializable {
    private int value;

    public Generate(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
