package colouring;

import java.util.*;
import graph.*;

public class Palette6 {

    Set<Solution6> elements;
    Graph originalGraph;
    public Vertex vertex;
    public Edge edgeA;
    public Edge edgeD;
    public Edge edgeG;

    public Palette6(Set<Solution6> elements, Graph originalGraph, Vertex vertex, Edge edgeA, Edge edgeD, Edge edgeG) {
        this.elements = elements;
        this.originalGraph = originalGraph;
        this.vertex = vertex;
        this.edgeA = edgeA;
        this.edgeD = edgeD;
        this.edgeG = edgeG;
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

    public Graph getOriginalGraph() {
        return originalGraph;
    }

    public boolean isSubsetOf(Palette6 other) {
        return other.getElements().containsAll(elements);
    }

    public boolean isSupersetOf(Palette6 other) {
        return elements.containsAll(other.getElements());
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Palette6 other)) return false;
        return elements.equals(other.getElements());
    }

    @Override
    public String toString() {
        return "Palette for graph\n" + originalGraph + "where\n" +
                "Vertex: " + vertex.getId() +
                ", Edge A: " + edgeA +
                ", Edge D: " + edgeD +
                ", Edge G: " + edgeG + "\n";
    }

    public String toStringModified(int x) {
        return "Palette " + x + " for graph\n" + originalGraph + "where\n" +
                "Vertex: " + vertex.getId() +
                " Edge A: " + edgeA +
                " Edge D: " + edgeD +
                " Edge G: " + edgeG + "\n";
    }
}
