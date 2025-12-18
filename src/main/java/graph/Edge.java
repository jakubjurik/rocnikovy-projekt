package graph;

import java.util.*;

public class Edge {
    private final int id;
    private Vertex from;
    private Vertex to;

    public Edge(int id, Vertex from, Vertex to) {
        if (from == to) {
            throw new IllegalArgumentException("Loops are not allowed");
        }
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public void exchangeVertex(Vertex oldVertex, Vertex newVertex) {
        if (oldVertex == from) {
            from = newVertex;
        } else if (oldVertex == to) {
            to = newVertex;
        } else {
            throw new IllegalArgumentException("Edge not incident with old vertex");
        }
        oldVertex.removeEdge(this);
        newVertex.addEdge(this);
    }

    public Set<Edge> getNeighbours() {
        Set<Edge> neighbours = new HashSet<>();
        neighbours.addAll(from.getEdges());
        neighbours.addAll(to.getEdges());
        neighbours.remove(this);
        return neighbours;
    }

    public Set<Edge> getNeighboursNeighbours() {
        Set<Edge> neighbours = new HashSet<>();
        List<Edge> fromEdges = new ArrayList<>(from.getEdges());
        fromEdges.remove(this);
        for (Edge edge : fromEdges) {
            neighbours.addAll(edge.getNeighbours());
        }
        List<Edge> toEdges = new ArrayList<>(to.getEdges());
        toEdges.remove(this);
        for (Edge edge : toEdges) {
            neighbours.addAll(edge.getNeighbours());
        }
        return neighbours;
    }

    public Set<Edge> getNeighboursWithinDistanceOfTwo() {
        Set<Edge> neighbours = new HashSet<>();
        neighbours.addAll(getNeighbours());
        neighbours.addAll(getNeighboursNeighbours());
        return neighbours;
    }

    @Override
    public String toString() {
        return id + " (" + from.getId() + " <-> " + to.getId() + ")";
    }

}
