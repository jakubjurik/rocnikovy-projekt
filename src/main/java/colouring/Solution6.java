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
        if (ce <= cf) {
            this.ce = ce;
            this.cf = cf;
        } else {
            this.ce = cf;
            this.cf = ce;
        }
        this.cg = cg;
        if (ch <= ci) {
            this.ch = ch;
            this.ci = ci;
        } else {
            this.ch = ci;
            this.ci = ch;
        }
    }

    public List<Integer> getColours() {
        return new ArrayList<>(Arrays.asList(cd, ce, cf, cg, ch, ci));
    }

    @Override
    public int hashCode() {
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

        /*
        return (cd == other.cd && cg == other.cg)
                && ((ce == other.ce && cf == other.cf) || (ce == other.cf && cf == other.ce))
                && ((ch == other.ch && ci == other.ci) || (ch == other.ci && ci == other.ch));

         */

    }

    @Override
    public String toString() {
        return cd + " " + ce + " " + cf + " " + cg + " " + ch + " " + ci;
    }
}
