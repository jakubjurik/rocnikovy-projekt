package colouring;

import java.util.*;

public class Solution6 {
    private final int cd;
    private final int ce;
    private final int cf;
    private final int cg;
    private final int ch;
    private final int ci;

    public Solution6(int cd, int ce, int cf, int cg, int ch, int ci) {
        this.cd = cd;
        this.ce = ce;
        this.cf = cf;
        this.cg = cg;
        this.ch = ch;
        this.ci = ci;
    }

    public List<Integer> getColours() {
        return new ArrayList<>(Arrays.asList(cd, ce, cf, cg, ch, ci));
    }

    @Override
    public int hashCode() {
        //return cd + ce + cf + cg + ch + ci;
        return Objects.hash(cd, ce, cf, cg, ch, ci);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Solution6 other)) return false;

        return cd == other.cd &&
                ce == other.ce &&
                cf == other.cf &&
                cg == other.cg &&
                ch == other.ch &&
                ci == other.ci;
    }

    @Override
    public String toString() {
        return cd + " " + ce + " " + cf + " " + cg + " " + ch + " " + ci;
    }
}
