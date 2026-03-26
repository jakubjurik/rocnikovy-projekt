package colouring;

import java.util.*;

public class Palette {

    private final Set<Solution> elements = new HashSet<>();

    public Palette() {}

    public Palette(Set<Solution> elements) {
        this.elements.addAll(elements);
    }

    public void add(Solution solution) {
        elements.add(solution);
    }

    public void remove(Solution solution) {
        elements.remove(solution);
    }

    public Set<Solution> getElements() {
        return elements;
    }

    public boolean isSubsetOf(Palette other) {
        return other.getElements().containsAll(elements);
    }

    public boolean isSupersetOf(Palette other) {
        return elements.containsAll(other.getElements());
    }

    public boolean isEqual(Palette other) {
        return elements.equals(other.getElements());
    }
}
