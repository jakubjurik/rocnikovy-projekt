package graph;

import java.util.*;

public class Vertex {
    private final int id;
    private final Set<Edge> edges;

    public Vertex(int id) {
        this.id = id;
        this.edges = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        /*
        if (edges.size() >= 3) {
            throw new IllegalArgumentException("Too many edges");
        }
         */
        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Set<Vertex> getNeighbours() {
        Set<Vertex> neighbours = new HashSet<>();

        for (Edge e : edges) {
            Vertex neighbour;
            if (e.getFrom() == this) {
                neighbour = e.getTo();
            } else {
                neighbour = e.getFrom();
            }
            neighbours.add(neighbour);
        }

        return neighbours;
    }

}
