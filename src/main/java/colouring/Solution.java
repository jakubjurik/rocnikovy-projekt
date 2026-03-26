package colouring;

import java.util.*;

public class Solution {
    private final int ca;
    private final int cb;
    private final int cc;
    private final int cd;
    private final int ce;
    private final int cf;
    private final int cg;
    private final int ch;
    private final int ci;

    public Solution(int ca, int cb, int cc, int cd, int ce, int cf, int cg, int ch, int ci) {
        this.ca = ca;
        this.cb = cb;
        this.cc = cc;
        this.cd = cd;
        this.ce = ce;
        this.cf = cf;
        this.cg = cg;
        this.ch = ch;
        this.ci = ci;
    }

    public List<Integer> getColours() {
        return new ArrayList<>(Arrays.asList(ca, cb, cc, cd, ce, cf, cg, ch, ci));
    }


    @Override
    public int hashCode() {
        //return ca + cb + cc + cd + ce + cf + cg + ch + ci;
        return Objects.hash(ca, cb, cc, cd, ce, cf, cg, ch, ci);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Solution other)) return false;

        return ca == other.ca &&
                cb == other.cb &&
                cc == other.cc &&
                cd == other.cd &&
                ce == other.ce &&
                cf == other.cf &&
                cg == other.cg &&
                ch == other.ch &&
                ci == other.ci;
    }

     //*/

    /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Solution other)) return false;

        Map<Integer, Set<Integer>> thisMap = Map.of(
                ca, Set.of(cb, cc),
                cd, Set.of(ce, cf),
                cg, Set.of(ch, ci)
        );

        Map<Integer, Set<Integer>> otherMap = Map.of(
                other.ca, Set.of(other.cb, other.cc),
                other.cd, Set.of(other.ce, other.cf),
                other.cg, Set.of(other.ch, other.ci)
        );

        return thisMap.equals(otherMap);
    }

    @Override
    public int hashCode() {
        Map<Integer, Set<Integer>> map = Map.of(
                ca, Set.of(cb, cc),
                cd, Set.of(ce, cf),
                cg, Set.of(ch, ci)
        );
        return map.hashCode();
    }

     */

    /*
    @Override
    public int hashCode() {
        return Objects.hash(ca, cb, cc, cd, ce, cf, cg, ch, ci);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Solution other)) return false;

        if (other.ca == ca) {
            if (!((other.cb == cb || other.cb == cc) && (other.cc == cb || other.cc == cc))) return false;
            if (other.cd == cd) {
                if (!((other.ce == ce || other.ce == cf) && (other.cf == ce || other.cf == cf))) return false;
                if (other.cg == cg) {
                    return (other.ch == ch || other.ch == ci) && (other.ci == ch || other.ci == ci);
                } else return false;
            } else if (other.cd == cg) {
                if (!((other.ce == ch || other.ce == ci) && (other.cf == ch || other.cf == ci))) return false;
                if (other.cg == cd) {
                    return (other.ch == ce || other.ch == cf) && (other.ci == ce || other.ci == cf);
                } else return false;
            } else return false;
        } else if (other.ca == cd) {
            if (!((other.cb == ce || other.cb == cf) && (other.cc == ce || other.cc == cf))) return false;
            if (other.cd == ca) {
                if (!((other.ce == cb || other.ce == cc) && (other.cf == cb || other.cf == cc))) return false;
                if (other.cg == cg) {
                    return (other.ch == ch || other.ch == ci) && (other.ci == ch || other.ci == ci);
                } else return false;
            } else if (other.cd == cg) {
                if (!((other.ce == ch || other.ce == ci) && (other.cf == ch || other.cf == ci))) return false;
                if (other.cg == ca) {
                    return (other.ch == cb || other.ch == cc) && (other.ci == cb || other.ci == cc);
                } else return false;
            } else return false;
        } else if (other.ca == cg) {
            if (!((other.cb == ch || other.cb == ci) && (other.cc == ch || other.cc == ci))) return false;
            if (other.cd == ca) {
                if (!((other.ce == cb || other.ce == cc) && (other.cf == cb || other.cf == cc))) return false;
                if (other.cg == cd) {
                    return (other.ch == ce || other.ch == cf) && (other.ci == ce || other.ci == cf);
                } else return false;
            } else if (other.cd == cd) {
                if (!((other.ce == ce || other.ce == cf) && (other.cf == ce || other.cf == cf))) return false;
                if (other.cg == ca) {
                    return (other.ch == cb || other.ch == cc) && (other.ci == cb || other.ci == cc);
                } else return false;
            } else return false;

        } else return false;
    }

     //*/

    @Override
    public String toString() {
        return ca + " " + cb + " " + cc + " " + cd + " " + ce + " " + cf + " " + cg + " " + ch + " " + ci;
    }
}
