package colouring;

import java.util.*;

public class Palette6 {

    private final Set<Solution6> elements = new HashSet<>();

    public Palette6() {}

    public Palette6(Set<Solution6> elements) {
        this.elements.addAll(elements);
    }

    public void add(Solution6 solution) {
        elements.add(solution);
    }

    public void remove(Solution6 solution) {
        elements.remove(solution);
    }

    public Set<Solution6> getElements() {
        return elements;
    }

    public boolean isSubsetOf(Palette6 other) {
        return other.getElements().containsAll(elements);
    }

    public boolean isSupersetOf(Palette6 other) {
        return elements.containsAll(other.getElements());
    }

    public boolean isEqual(Palette6 other) {
        return elements.equals(other.getElements());
    }
}
